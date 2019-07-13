package com.jnshu.studio.mapper;

import com.jnshu.studio.model.Comments;
import com.jnshu.studio.model.vo.CommentSearchVO;

import java.util.List;

public interface CommentsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Comments record);

    Long insertSelective(Comments record);

    Comments selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comments record);

    int updateByPrimaryKey(Comments record);

    List<Comments> selectComments(Comments comments);

    /*关联查询测试*/
    List<CommentSearchVO> selectCommentsWorks(CommentSearchVO commentSearchVO);

}