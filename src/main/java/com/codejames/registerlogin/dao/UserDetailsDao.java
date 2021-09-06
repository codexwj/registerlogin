package com.codejames.registerlogin.dao;

import com.codejames.registerlogin.entity.UserDetails;
import com.codejames.registerlogin.mapper.UserDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsDao {

    @Autowired
    UserDetailsMapper userDetailsMapper;

    public UserDetails getUserDetails(String username){
        return userDetailsMapper.getUserDetails(username);
    }

    public void registerUser(UserDetails userDetails){
        userDetailsMapper.registerUser(userDetails);
    }
}
