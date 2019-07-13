package com.jnshu.studio.controller;


import com.jnshu.studio.model.Navigation;
import com.jnshu.studio.model.Works;
import com.jnshu.studio.service.NavigationService;
import com.jnshu.studio.service.WorksService;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.List;

@Controller
@RequestMapping(value = "/studio/backstage/works")
public class WorksController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    WorksService worksService;

    @Autowired
    NavigationService navigationService;


    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<Object> selectWorks(Works works, long pid) {
        List<Object> list = new ArrayList<>();
        /*根据父导航ID查作品*/
        works.setNavigationId(pid);
        List<Works> worksList = worksService.selectWorks(works);
        logger.info("查询到的作品{}", worksList);
        /**定义一个内部类进行导航的查询*/
        class Level {
            /*保存导航的集合*/
            private List<List<Navigation>> navigationLevelAll = new ArrayList<>();

            /**
             * 导航递归调用
             * @param pid 作品父导航id值
             */
            private List<List<Navigation>> navigationLevel(long pid) {
                Navigation navigation = new Navigation();
                navigation.setId(pid);
                /*查询父导航数据*/
                List<Navigation> navigationList = navigationService.selectNavigation(navigation);
                /*将查询出的数据添加进集合*/
                navigationLevelAll.add(navigationList);
                /*遍历数据，判断导航等级*/
                for (Navigation navigation1 : navigationList) {
                    logger.info("递归查出来的{}级导航参数是{}", navigation1.getGrade(), navigation1);
                    /*如果查出的导航等级不为0，即此导航不是顶级导航，继续递归*/
                    if (navigation1.getGrade() != 0) {
                        navigationLevel(navigation1.getFather());
                    }
                }
                return navigationLevelAll;
            }
        }
        /*使用递归查询上级导航*/
        list.add(new Level().navigationLevel(pid));
        list.add(worksList);


        return list;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public List<Object> delete(@RequestBody long id) {
        List<Object> list = new ArrayList<>();
        try {
            Validate.isTrue(worksService.deleteByPrimaryKey(id) == 1);
            Integer code = 1;
            list.add(code);
            String msg = "操作成功";
            list.add(msg);
        } catch (Exception e) {
            e.printStackTrace();
            Integer code = -1;
            list.add(code);
            String msg = "操作失败";
            list.add(msg);
        }
        return list;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public List<Object> insertSelective(@RequestBody Works works) {

        List<Object> list = new ArrayList<>();
        try {
            Validate.notEmpty(works.getTitle());
            works.setStatus(10);
            works.setCreateAt(System.currentTimeMillis());
            works.setUpdateAt(System.currentTimeMillis());
            works.setCreateBy("没人");
            works.setUpdateBy("没人");
            worksService.insertSelective(works);
            list.add(works.getId());
            Integer code = 1;

            list.add(code);
            String msg = "操作成功";
            list.add(msg);

        } catch (Exception e) {
            e.printStackTrace();
            Integer code = -1;
            list.add(code);
            String msg = "操作失败";
            list.add(msg);
        }
        return list;
    }

    /**取名字问题*/
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public List updateWorks(@RequestBody Works works) {
        List<Object> list = new ArrayList<>();
        try {
            works.setUpdateAt(System.currentTimeMillis());
            worksService.updateByPrimaryKeySelective(works);
            String msg="操作成功";
            int code=1;
            list.add(code);
            list.add(msg);
        } catch (Exception e) {
            e.printStackTrace();
            String msg="操作失败";
            int code=-1;
            list.add(code);
            list.add(msg);
        }
        return list;
    }
}
