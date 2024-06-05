package com.lifh.diary.model.dto;

import lombok.Data;

/**
 * @author li_fh
 * @description 接收页面的用户注册表单信息
 */
@Data
public class UserRegisterDto {
    /**
     * 账号
     */
    private String account;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 校验密码
     */
    private String checkPassword;
    /**
     * 性别
     */
    private Integer gender;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}
