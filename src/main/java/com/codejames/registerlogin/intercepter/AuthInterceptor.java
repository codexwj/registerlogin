package com.codejames.registerlogin.intercepter;

import com.codejames.registerlogin.config.AuthChecker;
import com.codejames.registerlogin.constant.NormalConstant;
import com.codejames.registerlogin.entity.JsonData;
import com.codejames.registerlogin.util.JsonUtils;
import com.codejames.registerlogin.util.TokenHelper;
import com.codejames.registerlogin.util.TokenModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;



@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {


    @Autowired
    TokenHelper tokenHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //如果被@NoneAuth注解代表需要登录验证
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.getAnnotation(AuthChecker.class) != null || handlerMethod.getBeanType().getAnnotation(AuthChecker.class) != null)
        {

            //token验证
            String token = request.getHeader(NormalConstant.AUTHORIZATION);

            TokenModel model = tokenHelper.get(token);

            //验证通过
            if (tokenHelper.check(model)) {
                request.setAttribute(NormalConstant.CURRENT_USER_ID, model.getUserId());
                return true;
            }
            //验证未通过
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(JsonUtils.obj2String(JsonData.buildError(401, "权限未认证")));
            return false;
        }else {
            return true;
        }
    }
}



