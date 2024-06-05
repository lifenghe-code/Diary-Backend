package com.lifh.diary.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 日记
 * @TableName dairy
 */
@TableName(value ="dairy")
@Data
public class Diary implements Serializable {
    /**
     * dairy_id
     */
    @TableId(type = IdType.AUTO)
    private Integer diaryId;

    /**
     * 日记标题
     */
    private String title;

    /**
     * 日记内容
     */
    private String content;

    /**
     * 外键，关联到用户信息表中的用户ID，表示文章的作者
     */
    private Integer authorId;

    /**
     * 状态 0 - 正常
     */
    private Integer status;

    /**
     * 日记类型 0 - 公开 1 - 私有 2 - 未发表
     */
    private Integer type;

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
     * 发表时间
     */
    private Date publishTime;

    /**
     * 
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}