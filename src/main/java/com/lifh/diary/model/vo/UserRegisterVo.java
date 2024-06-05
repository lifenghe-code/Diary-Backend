package com.lifh.diary.model.vo;

import lombok.Data;

/**
 * @author li_fh
 * @description 返回给前端的信息
 */
@Data
public class UserRegisterVo {
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
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}
