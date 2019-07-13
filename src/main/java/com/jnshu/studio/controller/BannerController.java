package com.jnshu.studio.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jnshu.studio.model.Banner;
import com.jnshu.studio.service.BannerService;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/studio")
public class BannerController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    BannerService bannerService;

    /**
     * 前台banner
     */
    @RequestMapping(value = "/banner", method = RequestMethod.GET)
    public Map<String, Object> selectBanner(Banner banner, Integer page, Integer size) {
        HashMap<String, Object> map = new HashMap<>(16);
        logger.info("查询Banner时传入的值{}", banner);
        try {
            /*页数与行数为空时默认值*/
            if (page == null || size == null) {
                page = 1;
                size = 10;
            }
            /*创建data值集合*/
            HashMap<String, Object> data = new HashMap<>();
            String orderBy = "status desc,sort,update_at desc";
            PageHelper.startPage(page, size, orderBy);
            logger.info("使用页数：{}--行数：{}与查询的值{}", page, size, banner);
            List<Banner> list = bannerService.selectBanner(banner);
            logger.info("查询出的值{}", list);
            /*分页插件获取信息，total：记录总数，page第几页，pageSize页面记录数设置*/
            PageInfo<Banner> pageInfo = new PageInfo<>(list);
            data.put("total", pageInfo.getTotal());
            data.put("page", pageInfo.getPages());
            data.put("pageSize", pageInfo.getPageSize());
            data.put("bannerList", list);
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
     * 后台banner
     */
    @RequestMapping(value = "/backstage/banner", method = RequestMethod.GET)
    public Map<String, Object> selectBackstageBanner(Banner banner, Integer page, Integer size) {
        HashMap<String, Object> map = new HashMap<>(16);
        logger.info("查询Banner时传入的值{}", banner);
        try {
            /*页数与行数为空时默认值*/
            if (page == null || size == null) {
                page = 1;
                size = 10;
            }
            /*创建data值集合*/
            HashMap<String, Object> data = new HashMap<>(16);
            /*设置排序：字段空格排序方式*/
            String orderBy = "status desc,sort,update_at desc";
            PageHelper.startPage(page, size, orderBy);
            logger.info("使用页数：{}--行数：{}与查询的值{}", page, size, banner);
            List<Banner> list = bannerService.selectBanner(banner);
            logger.info("查询出的值{}", list);
            /*分页插件获取信息，total：记录总数，page第几页，size页面记录数设置*/
            PageInfo<Banner> pageInfo = new PageInfo<>(list);
            data.put("total", pageInfo.getTotal());
            data.put("page", pageInfo.getPages());
            data.put("size", pageInfo.getPageSize());
            data.put("bannerList", list);
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

    @RequestMapping(value = "/backstage/banner", method = RequestMethod.DELETE)
    public HashMap<String, Object> deleteByPrimaryKey(@RequestBody long id) {
        HashMap<String, Object> map = new HashMap<>(16);
        logger.info("要删除的ID值{}", id);
        try {
            bannerService.deleteByPrimaryKey(id);
            map.put("code", 1);
            map.put("message", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("message", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/backstage/banner", method = RequestMethod.POST)
    public HashMap<String, Object> insertSelective(@RequestBody Banner banner) {
        HashMap<String, Object> map = new HashMap<>(16);
        logger.info("前端传进新增的值{}", banner);
        try {
            /*校验*/
            Validate.notEmpty(banner.getImage());
            Validate.notEmpty(banner.getUrl());
            banner.setStatus(10);
            banner.setUpdateAt(System.currentTimeMillis());
            banner.setCreateAt(System.currentTimeMillis());
            logger.info("完整新增的值{}", banner);
            bannerService.insertSelective(banner);
            map.put("id", banner.getId());
            map.put("code", 1);
            map.put("message", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("message", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/backstage/banner", method = RequestMethod.PUT)
    public HashMap<String, Object> updateByPrimaryKeySelective(@RequestBody Banner banner) {
        HashMap<String, Object> map = new HashMap<>(16);
        logger.info("前端传进要修改的值{}", banner);
        try {
            Validate.notEmpty(banner.getImage());
            Validate.notEmpty(banner.getUrl());
            /*判断要修改的某个值，且判断此数据在数据库内是否达到规定上限*/
            int setStatusUp = 20;
            int setStatusDown=10;
            int statusMax = 6;
            if (banner.getStatus() == setStatusUp) {
                /*用分页插件判断数据库已存状态值的数量*/
                Banner banner1 = new Banner();
                banner1.setStatus(setStatusUp);
                List<Banner> list = bannerService.selectBanner(banner1);
                PageInfo<Banner> pageInfo = new PageInfo<>(list);
                long statusTotal = pageInfo.getTotal();
                logger.info("状态值等于{}的有{}个", setStatusUp, statusTotal);
                /*判断是否达到规定上限值*/
                Validate.isTrue(statusTotal < statusMax);
                banner.setUpdateAt(System.currentTimeMillis());
                bannerService.updateByPrimaryKeySelective(banner);
                map.put("code", 11);
                map.put("message", "操作成功");
            }
            /*如果是下架操作，将清除权重值*/
            else if (banner.getStatus() == setStatusDown){
                banner.setUpdateAt(System.currentTimeMillis());
                banner.setSort(null);
                bannerService.updateByPrimaryKeySelective(banner);
                map.put("code", 1);
                map.put("message", "操作成功");
            } else{
                banner.setUpdateAt(System.currentTimeMillis());
                bannerService.updateByPrimaryKeySelective(banner);
                map.put("code", 1);
                map.put("message", "操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("message", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/backstage/banner/sort", method = RequestMethod.PUT)
    public HashMap<String, Object> updateByPrimaryKeySelectiveStatus(@RequestBody List<Long> id, Banner banner) {
        HashMap<String, Object> map = new HashMap<>(16);
        logger.info("前端传进要修改的权重id：{}", id);
        try {
            int i = 0;
            for (Long sort : id) {
                banner.setId(sort);
                banner.setSort(i);
                logger.info("修改ID{}的权重值为{}", sort, i);
                i++;
                banner.setUpdateAt(System.currentTimeMillis());
                bannerService.updateByPrimaryKeySelective(banner);
            }
            map.put("code", 1);
            map.put("message", "操作成功");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("message", "操作失败");
        }
        return map;
    }
}
