package com.codejames.registerlogin.mapper;

import com.codejames.registerlogin.entity.UserDetails;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDetailsMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO user_details (username, password, phoneNumber, region) values (#{username},#{password},#{phoneNumber},#{region})")
    void registerUser(UserDetails userDetails);

    @Select("SELECT * FROM user_details WHERE username = #{username}")
    UserDetails getUserDetails(String username);

    @Select("SELECT * FROM user_details WHERE id = #{userId}")
    UserDetails getUserDetailsById(Integer userId);

}
