package com.jnshu.studio.service;

import com.jnshu.studio.mapper.CommentsMapper;
import com.jnshu.studio.model.Comments;
import com.jnshu.studio.model.vo.CommentSearchVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Resource
    CommentsMapper commentsMapper;

    @Override
    public List<Comments> selectComments(Comments comments) {
        return commentsMapper.selectComments(comments);
    }

    @Override
    public List<CommentSearchVO> selectCommentsWorks(CommentSearchVO commentSearchVO) {
        return commentsMapper.selectCommentsWorks(commentSearchVO);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return commentsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Long insertSelective(Comments comments) {
        return commentsMapper.insertSelective(comments);
    }

    @Override
    public Comments selectByPrimaryKey(Long id) {
        return commentsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Comments comments) {
        return commentsMapper.updateByPrimaryKeySelective(comments);
    }
}