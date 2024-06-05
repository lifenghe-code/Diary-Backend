package com.lifh.diary.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class UserVo {
    /**
     * id
     */
    private Integer userId;
    /**
     * 账号
     */
    private String account;

    /**
     * 用户昵称
     */
    private String username;


    /**
     * 性别
     */
    private Integer gender;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户角色 0 - 普通用户 1 - 管理员
     */
    private Integer role;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     *
     */
    @TableField("update_time")
    private Date updateTime;
}
