package com.codejames.registerlogin.entity;

import lombok.Data;

@Data
public class UserDetails {
    Integer id;
    String username;
    String password;
    String phoneNumber;
    String region;

    public UserDetails(String username, String password, String phoneNumber, String region) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.region = region;
    }

    public UserDetails(String username, String phoneNumber, String region) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.region = region;
    }

//    public UserDetails(Integer id, String username, String password, String phoneNumber, String region) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.phoneNumber = phoneNumber;
//        this.region = region;
//    }

    public UserDetails() {
    }
}
