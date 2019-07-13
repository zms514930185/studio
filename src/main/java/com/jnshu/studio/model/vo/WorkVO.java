package com.jnshu.studio.model.vo;

import com.jnshu.studio.model.Comments;
import com.jnshu.studio.model.Works;

import java.util.List;

public class WorkVO extends Works {
    private List<Comments> commentsList;

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }
}
