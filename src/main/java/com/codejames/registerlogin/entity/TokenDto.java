package com.codejames.registerlogin.entity;

import lombok.Data;

@Data
public class TokenDto {
    private String token;

    // token创建时间
    private Long tokenCreatedTime;
    //失效时间
    private Long tokenExpiryTime;

    private String isLogin;

}
