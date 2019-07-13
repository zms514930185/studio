package com.jnshu.studio.service;

import com.jnshu.studio.model.Works;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
class WorksServiceImplTest {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private ApplicationContext zms = new ClassPathXmlApplicationContext("applicationContext.xml");
    private WorksService worksService = zms.getBean(WorksService.class);

   /* @Autowired
    WorksService worksService;
*/
    @Test
    void selectWorks(){
        Works works = new Works();
        works.setTitle("t");
        logger.info(worksService.selectWorks(works));
    }
}