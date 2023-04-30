package com.bowen.interceptor;


import com.bowen.annotation.LoginRequired;
import com.bowen.annotation.PermissionRequired;
import com.bowen.bean.User;
import com.bowen.bean.UserTypeEnum;
import com.bowen.constants.WebConstant;
import com.bowen.exception.BizException;
import com.bowen.exception.ExceptionCodeEnum;
import com.bowen.util.ThreadLocalUtil;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * @author bowen
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //放行跨域相关
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }

        //判断是否免登陆,是直接放行
        if(isLoginFree(handler)){
            return true;
        }

        //校验用户密码是否正确
        User user = handleLogin(request,response);


        //校验用户是否有权限
        checkPermission(user,handler);

        ThreadLocalUtil.put(WebConstant.USER_INFO,user);


        return super.preHandle(request, response, handler);
    }

    private void checkPermission(User user, Object handler) {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            PermissionRequired permission = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), PermissionRequired.class);
            if(permission == null){
                return;
            }else{
                if(UserTypeEnum.hasPermission(permission.userType(), user.getUserType(), permission.logical())){
                    return;
                }
            }
            throw new BizException(ExceptionCodeEnum.PERMISSION_DENY);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove(WebConstant.USER_INFO);
        super.afterCompletion(request, response, handler, ex);
    }

    private User handleLogin(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User currentUser = (User)session.getAttribute(WebConstant.USER_INFO);
        if (currentUser == null) {
            throw new BizException(ExceptionCodeEnum.NEED_LOGIN);
        }
        return currentUser;
    }

    private boolean isLoginFree(Object handler) {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            Method method = handlerMethod.getMethod();
            LoginRequired loginRequired = AnnotationUtils.findAnnotation(method, LoginRequired.class);
            return loginRequired == null;
        }
        return true;
    }
}
