package com.jnshu.studio.model.vo;

import com.jnshu.studio.model.Comments;
import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class CommentVO extends Comments {
    private String workName;


    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }
}
