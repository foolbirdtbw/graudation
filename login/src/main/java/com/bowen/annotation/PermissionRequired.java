package com.bowen.annotation;

import com.bowen.bean.LogicalEnum;
import com.bowen.bean.UserTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bowen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@LoginRequired
public @interface PermissionRequired {
    /**
     * 角色
     * 默认游客权限
     * @return
     */
    UserTypeEnum[] userType() default {UserTypeEnum.TOURIST};

    /**
     * 逻辑关系,OR表示用户的用户类型是userType中的任意一种
     *          AND表示用户的用户类型同时满足userType这个暂时用不到
     * @return
     */
    LogicalEnum logical();
}
