package com.codejames.registerlogin.controller;

import com.codejames.registerlogin.annotation.NoneAuth;
import com.codejames.registerlogin.config.SystemControllerLog;
import com.codejames.registerlogin.dao.UserDetailsDao;
import com.codejames.registerlogin.entity.UserDetails;
import com.codejames.registerlogin.entity.JsonData;
import com.codejames.registerlogin.enums.HttpStatusEnum;
import com.codejames.registerlogin.constant.MessageConstant;
import com.codejames.registerlogin.constant.NormalConstant;
import com.codejames.registerlogin.util.TokenHelper;
import com.codejames.registerlogin.util.TokenModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.handler.MessageContext;
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

//    @NoneAuth
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
                return JsonData.buildError(HttpStatusEnum.RE_REGISTRATION.getCode(),HttpStatusEnum.RE_REGISTRATION.getInfo());
            }
        }

        //return null statement
        return new JsonData();
    }

    /* Origin edition
        @PostMapping(value = "/query/userdetails")
        public Outform getUserDetails(@RequestBody Map<String, Object> req) {
            String username = (String) req.get("username");
            if (username == null) {
                return new Outform();
            }
            UserDetails userDetails = userDetailsDao.getUserDetails(username);

            Map<String, Object> resultMap = new HashMap<>();
            Outform outform = new Outform();
            if (userDetails == null) {
                outform.setErrCode("500");
                outform.setErrMsg("user does not exists!!!");
                outform.setResult(username);
            } else {
                outform.setErrCode("200");
                outform.setErrMsg("query success!!!");
                outform.setResult(userDetails);
            }
            return outform;
        }
    */
    @PostMapping(value = "/query/userdetails")
    public Object getUserDetails(@RequestBody Map<String, Object> req) {
        String username = (String) req.get("username");
        if (username == null) {
            return JsonData.buildError(HttpStatusEnum.NOT_FOUND.getCode(), MessageConstant.USERNAME_OR_PASSWORD_ERROR);
        }
        UserDetails userDetails = userDetailsDao.getUserDetails(username);

        HashMap<String, Object> map = new HashMap<>();
        if (userDetails == null) {
            return JsonData.buildError(HttpStatusEnum.NOT_FOUND.getCode(), MessageConstant.USERNAME_OR_PASSWORD_ERROR);
        } else {
            String phoneNumber = (String) userDetails.getPhoneNumber();
            String region = (String) userDetails.getRegion();
            map.put("username", username);
            map.put("phoneNumber", phoneNumber);
            map.put("region", region);
            return JsonData.buildSuccess(map);
        }
    }

    @SystemControllerLog(description = "登入系统")
    @PostMapping("/login1")
    public Object login1(@RequestBody Map<String, Object> req) {
        String username = (String) req.get("username");
        String password = (String) req.get("password");

        UserDetails user = userDetailsDao.getUserDetails(username);
        if (user == null || !user.getPassword().equals(password)) {
            return JsonData.buildError(HttpStatusEnum.NOT_FOUND.getCode(), MessageConstant.USERNAME_OR_PASSWORD_ERROR);
        }

        TokenModel model = tokenHelper.create(user.getId());
        return JsonData.buildSuccess(model);
    }

//    @PostMapping(value = "/logout")
//    public Object logout(HttpServletRequest request) {
//        Integer userId = (Integer) request.getAttribute(NormalConstant.CURRENT_USER_ID);
//        System.out.println(userId);
//        if (userId != null) {
//            tokenHelper.delete(userId);
//        }
//        return JsonData.buildSuccess();
//    }

    @PostMapping(value = "/logout")
    public Object logout(@RequestBody Map<String,Object> request) {
//        Integer userId = (Integer) request.getAttribute(NormalConstant.CURRENT_USER_ID);
        Integer userId = (Integer)request.get("userId");
        System.out.println(userId);
        if (userId != null) {
            tokenHelper.delete(userId);
        }
        return JsonData.buildSuccess();
    }
}
