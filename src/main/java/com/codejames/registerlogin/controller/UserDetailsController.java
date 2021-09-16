package com.codejames.registerlogin.controller;

import com.codejames.registerlogin.aop.SystemControllerLog;
import com.codejames.registerlogin.config.AuthChecker;
import com.codejames.registerlogin.dao.UserDetailsDao;
import com.codejames.registerlogin.entity.UserDetails;
import com.codejames.registerlogin.entity.JsonData;
import com.codejames.registerlogin.enums.HttpStatusEnum;
import com.codejames.registerlogin.constant.MessageConstant;
import com.codejames.registerlogin.constant.NormalConstant;
import com.codejames.registerlogin.util.TokenHelper;
import com.codejames.registerlogin.util.TokenModel;
import com.codejames.registerlogin.util.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/")
public class UserDetailsController {
    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDetailsDao userDetailsDao;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private JwtUtils jwtUtils;

    @SystemControllerLog(description = "注册用户")
    @PostMapping(value = "register/user")
    public Object registerUser(@RequestBody Map<String, Object> req) throws Exception {
        String username = (String) req.get("username");
        String password = (String) req.get("password");
        String phoneNumber = (String) req.get("phoneNumber");
        String region = (String) req.get("region");
        System.out.println(username);
        UserDetails userDetails = new UserDetails(username, password, phoneNumber, region);

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }

        UserDetails userDetailsQuery = userDetailsDao.getUserDetails(username);

        if (userDetailsQuery == null) {

            userDetailsDao.registerUser(userDetails);
            return JsonData.buildSuccess(HttpStatusEnum.SUCCESS);

        } else {
            if (username.equals(userDetailsQuery.getUsername())) {
                return JsonData.buildError(HttpStatusEnum.RE_REGISTRATION.getCode(), HttpStatusEnum.RE_REGISTRATION.getInfo());
            }
        }

        //return null statement
        return new JsonData();
    }
//    @AuthChecker
    @PostMapping(value = "/query/userdetails")
    public Object getUserDetails(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        log.info("UserId:{}",userId);

        if (userId == null) {
            return JsonData.buildError(HttpStatusEnum.NOT_FOUND.getCode(), MessageConstant.USERNAME_OR_PASSWORD_ERROR);
        }

        UserDetails userDetails = userDetailsDao.getUserDetailsById(Integer.valueOf(userId));

        HashMap<String, Object> map = new HashMap<>();
        if (userDetails == null) {
            return JsonData.buildError(HttpStatusEnum.NOT_FOUND.getCode(), MessageConstant.USERNAME_OR_PASSWORD_ERROR);
        } else {
            String username = (String) userDetails.getUsername();
            String phoneNumber = (String) userDetails.getPhoneNumber();
            String region = (String) userDetails.getRegion();
            map.put("username", username);
            map.put("phoneNumber", phoneNumber);
            map.put("region", region);
            return JsonData.buildSuccess(map);
        }
    }

    @SystemControllerLog(description = "登入系统")
    @PostMapping("/login")
    public Object login1(@RequestBody Map<String, Object> req) {
        String username = (String) req.get("username");
        String password = (String) req.get("password");

        UserDetails user = userDetailsDao.getUserDetails(username);
        if (user == null || !user.getPassword().equals(password)) {
            return JsonData.buildError(HttpStatusEnum.NOT_FOUND.getCode(), MessageConstant.USERNAME_OR_PASSWORD_ERROR);
        }

//        TokenModel model = tokenHelper.create(user.getId());
        String generateToken = jwtUtils.generateToken(String.valueOf(user.getId()));
        TokenModel model = new TokenModel(user.getId(),generateToken);
        if (model == null) {
            log.info("登录失败");
            return null;
        } else {
            log.info("登录成功");
            return JsonData.buildSuccess(model);
        }
    }
//    @AuthChecker
    @PostMapping(value = "/logout")
    public Object logout(HttpServletRequest request) {
        /**
         * getHeader and getParameter testing
         */

        String token = request.getHeader("JWT");
        Claims claims = jwtUtils.getClaimsByToken(token);
        Date expiration = new Date();
        boolean tokenExpired = jwtUtils.isTokenExpired(expiration);

        log.info("The tokenExpired is real :{}",tokenExpired);
        String subject = claims.getSubject();
        if (subject == null){
            log.info("userId is does not exist!");
            return false;
        }
        String userId =(String) request.getAttribute("userId");
        System.out.println(userId);
        if (userId != null) {
            return JsonData.buildSuccess(userId);
        } else {
            return JsonData.buildError(HttpStatusEnum.FORBIDDEN.getCode(),HttpStatusEnum.FORBIDDEN.getInfo());
        }
    }

}
