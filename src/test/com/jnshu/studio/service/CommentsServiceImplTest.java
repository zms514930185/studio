package com.jnshu.studio.service;

import com.jnshu.studio.model.vo.CommentSearchVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

@Log4j2
class CommentsServiceImplTest {
    /*private final Logger logger = LogManager.getLogger(this.getClass());*/
    private ApplicationContext zms = new ClassPathXmlApplicationContext("applicationContext.xml");
    private CommentsService commentsService = zms.getBean(CommentsService.class);

    @Test
    void selectCommentsWorks() {
        CommentSearchVO commentSearchVO=new CommentSearchVO();
        commentSearchVO.setCommentsId(0L);
        commentSearchVO.setWorkName("1");

        log.info("要查的对象内的值{}",commentSearchVO);
        List<CommentSearchVO> commentSearchVOList=commentsService.selectCommentsWorks(commentSearchVO);

        log.info("测试{}",commentSearchVOList);
    }
    @Test
    void delect(){
        log.info("{}",commentsService.deleteByPrimaryKey(4L));
    }
}