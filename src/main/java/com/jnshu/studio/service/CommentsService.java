package com.jnshu.studio.service;

import com.jnshu.studio.model.Comments;
import com.jnshu.studio.model.vo.CommentSearchVO;


import java.util.List;

public interface CommentsService {
    List<Comments> selectComments(Comments comments);

    List<CommentSearchVO> selectCommentsWorks(CommentSearchVO commentSearchVO);

    int deleteByPrimaryKey(Long id);

    Long insertSelective(Comments comments);

    Comments selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comments comments);
}
