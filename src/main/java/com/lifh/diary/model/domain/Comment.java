package com.lifh.diary.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 评论
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    /**
     * comment_id
     */
    @TableId(type = IdType.AUTO)
    private Integer commentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 外键，关联到用户表中的用户ID，表示文章的作者
     */
    private Integer authorId;

    /**
     * 外键，关联到日记表中的日记ID，表示该评论的文章
     */
    private Integer diaryId;

    /**
     * 状态 0 - 正常
     */
    private Integer status;

    /**
     * 是否删除 0 - 正常
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}