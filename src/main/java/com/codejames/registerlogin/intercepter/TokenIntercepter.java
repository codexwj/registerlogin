package com.codejames.registerlogin.intercepter;

import com.codejames.registerlogin.entity.JsonData;
import com.codejames.registerlogin.enums.HttpStatusEnum;
import com.codejames.registerlogin.util.JsonUtils;
import com.codejames.registerlogin.util.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.authenticator.SpnegoAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class TokenIntercepter extends HandlerInterceptorAdapter {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("JWT");

        if (token == null || "".equals(token)){
            response.getWriter().write(JsonUtils.obj2String(JsonData.buildError(401, "权限未认证")));
            log.info("token is Invalid");
            return false;
        }

        // 解析token
        Claims claims = jwtUtils.getClaimsByToken(token);

        if (claims == null){
            response.getWriter().write(JsonUtils.obj2String(JsonData.buildError(401, "权限未认证")));
            log.info("token 解析失败");
            return false;
        }

        Date expiration = claims.getExpiration();
        boolean tokenExpired = jwtUtils.isTokenExpired(expiration);
        if (tokenExpired){
            response.getWriter().write(JsonUtils.obj2String(JsonData.buildError(401, "权限未认证")));
            log.info("token 已经过期, 请重新登录");
            return false;
        }
        String subject = claims.getSubject();
        if (subject == null){
            response.getWriter().write(JsonUtils.obj2String(JsonData.buildError(401, "权限未认证")));
            log.info("userId is does not exist!");
            return false;
        }
        request.setAttribute("userId",subject);
        return true;
    }
}
