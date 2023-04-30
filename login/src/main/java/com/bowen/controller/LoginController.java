package com.bowen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bowen.annotation.LoginRequired;
import com.bowen.annotation.PermissionRequired;
import com.bowen.bean.LogicalEnum;
import com.bowen.bean.User;
import com.bowen.bean.UserTypeEnum;
import com.bowen.common.Result;
import com.bowen.constants.WebConstant;
import com.bowen.exception.ExceptionCodeEnum;
import com.bowen.mapper.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author bowen
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;


    @PostMapping("/register")
    public Result needLogin(@RequestBody User user){
        int rows = userService.insert(user);
        if (rows > 0 ){
            return Result.success(user);
        }
        return Result.error(ExceptionCodeEnum.ERROR,"插入失败");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User loginInfo){
        LambdaQueryWrapper<User> userQueryVo = new LambdaQueryWrapper<>();
        userQueryVo.eq(User::getName,loginInfo.getName());
        userQueryVo.eq(User::getPassword,loginInfo.getPassword());
        User user = userService.selectOne(userQueryVo);
        if(Objects.nonNull(user)){
            session.setAttribute(WebConstant.USER_INFO,user);
            return Result.success("log in success!");
        }
        return Result.error(ExceptionCodeEnum.ERROR,"用户名或密码错误");
    }

    @RequestMapping("hello")
    @LoginRequired
    public Result needLogin(HttpServletRequest request){
        return Result.success(String.format("Hello, my friend %s",request.getSession().getAttribute(WebConstant.USER_INFO)));
    }

    @RequestMapping("travel")
    public Result unLogin(){
        return Result.success("if you see this, you are logged in.");
    }

    @PostMapping("school")
    public Result goToSchool(){
        return Result.success("if you see this, you are logged in");
    }

    @RequestMapping("admin")
    @PermissionRequired(userType = {UserTypeEnum.ADMIN},logical = LogicalEnum.OR)
    public Result admin(){
        return Result.success("if you see this, you are permission to log in");
    }



}
