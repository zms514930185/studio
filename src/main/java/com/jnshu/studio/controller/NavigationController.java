package com.jnshu.studio.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jnshu.studio.model.Navigation;
import com.jnshu.studio.service.NavigationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 字决
 */
@Controller
@RequestMapping(value = "/studio")
public class NavigationController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    NavigationService navigationService;

    /**
     * 查询一级导航
     */
    @ResponseBody
    @RequestMapping(value = "/backstage/navigation", method = RequestMethod.GET)
    public Map<String, Object> selectNavigation(Navigation navigation, Integer page, Integer size) {
        HashMap<String, Object> map = new HashMap<>(16);
        logger.info("查询导航时传入的值{}", navigation);
        try {
            /*创建data值集合*/
            HashMap<String, Object> data = new HashMap<>(16);
            /*页数与行数为空时默认值*/
            if (page == null || size == null) {
                page = 1;
                size = 10;
            }
            /*页数，排序规则设置*/
            String orderBy = "status desc,sort,update_at desc";
            PageHelper.startPage(page, size, orderBy);
            logger.info("使用页数：{}--行数：{}与查询的值{}", page, size, navigation);

            Map<Navigation, List<Navigation>> navigationLevel = new HashMap<>(16);
            if (navigation.getFather() == null) {
                navigation.setGrade(0);
                /*查询出所有顶级导航*/
                List<Navigation> list = navigationService.selectNavigation(navigation);
                logger.info("查询出的值{}", list);
                for (Navigation navigation1 : list) {
                    Navigation navigation2 = new Navigation();
                    navigation2.setFather(navigation1.getId());
                    /*查询出每个顶级导航的子导航*/
                    List<Navigation> list1 = navigationService.selectNavigation(navigation2);
                    navigationLevel.put(navigation1, list1);
                }
                PageInfo<Navigation> pageInfo = new PageInfo<>(list);
                data.put("total", pageInfo.getTotal());
                data.put("page", pageInfo.getPages());
                data.put("size", pageInfo.getPageSize());
                data.put("list", navigationLevel);
            } else {
                List<Navigation> list = navigationService.selectNavigation(navigation);
                PageInfo<Navigation> pageInfo = new PageInfo<>(list);
                data.put("total", pageInfo.getTotal());
                data.put("page", pageInfo.getPages());
                data.put("size", pageInfo.getPageSize());
                data.put("list", list);
            }
            /*分页插件获取信息，total：记录总数，page第几页，size页面记录数设置,list查询到的数据*/
            map.put("code", 1);
            map.put("message", "操作成功");
            map.put("data", data);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("message", "操作失败");
        }
        return map;
    }

    /**
     * 删除导航
     */
    @ResponseBody
    @RequestMapping(value = "/backstage/navigation", method = RequestMethod.DELETE)
    public int deleteNavigation(@RequestBody long id) {
        logger.info("删除id为{}的记录", id);
        return navigationService.deleteByPrimaryKey(id);
    }

    /**
     * 修改导航
     */
    @ResponseBody
    @RequestMapping(value = "/backstage/navigation", method = RequestMethod.PUT)
    public int updateNavigation(@RequestBody Navigation navigation) {
        logger.info("要修改的记录id:{}，名称修改为:{},要修改为状态为:{}", navigation.getId(), navigation.getName(), navigation.getStatus());
        return navigationService.updateByPrimaryKeySelective(navigation);
    }

    /**
     * 增加一条导航
     */
    @ResponseBody
    @RequestMapping(value = "/backstage/navigation", method = RequestMethod.POST)
    public int insertNavigation(@RequestBody Navigation navigation) {
        logger.info("增加一个父级导航{}", navigation.getName());
        if (navigation.getFather() == null) {
            navigation.setGrade(0);
        }
        return navigationService.insert(navigation);
    }
}
