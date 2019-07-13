package com.jnshu.studio.service;

import com.jnshu.studio.model.Navigation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class NavigationServiceImplTest {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private ApplicationContext zms = new ClassPathXmlApplicationContext("applicationContext.xml");
    private NavigationService navigationService = zms.getBean(NavigationService.class);

    @Test
    void selectNavigationAll(){
     logger.info(navigationService.selectNavigationAll());
    }

    @Test
    void selectNavigation(){
        Navigation navigation=new Navigation();
        navigation.setGrade(0);
        logger.info(navigationService.selectNavigation(navigation));
    }

    @Test
    void deleteNavigation(){
        logger.info(navigationService.deleteByPrimaryKey(1L));
    }

    @Test
    void updateNavigation(){
        Navigation navigation=new Navigation();
        navigation.setId(6L);
        //navigation.setName("诗集");
        navigation.setStatus(10);
        logger.info(navigationService.updateByPrimaryKeySelective(navigation));
    }

    @Test
    void insert(){
        Navigation navigation=new Navigation();
        navigation.setName("照片");
        logger.info(navigationService.insert(navigation));
    }

    @Test
    void delete(){
        navigationService.deleteByPrimaryKey(6L);
    }
}

