package com.jnshu.studio.service;

import com.github.pagehelper.PageInfo;
import com.jnshu.studio.model.Banner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BannerServiceImplTest {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private ApplicationContext zms = new ClassPathXmlApplicationContext("applicationContext.xml");
    private BannerService bannerService = zms.getBean(BannerService.class);

    @Test
    void selectBanner(){
        Banner banner=new Banner();
        banner.setStatus(20);
        List<Banner> list = bannerService.selectBanner(banner);
        logger.info(list);
        PageInfo<Banner> pageInfo = new PageInfo<>(list);
        logger.info(pageInfo.getTotal());
    }
    @Test
    void deleteByPrimaryKey(){
        logger.info("删除成功{}个",bannerService.deleteByPrimaryKey(4L));
    }
}