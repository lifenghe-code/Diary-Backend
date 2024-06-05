package com.lifh.diary.model.dto;

import lombok.Data;

@Data
public class UserLoginDto {
    /**
     * 账号
     */
    private String account;


    /**
     * 密码
     */
    private String password;
}
