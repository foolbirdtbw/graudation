package com.bowen.bean;


import java.util.Objects;

/**
 * @author bowen
 */

public enum UserTypeEnum {
    /**
     * 用户类型枚举
     */
    TOURIST(1,"游客"),
    STUDENT(2,"学生"),
    TEACHER(3,"教师"),
    ADMIN(4,"管理员");


    UserTypeEnum(Integer userTypeCode,String userTypeName){
        this.userTypeCode = userTypeCode;
        this.userTypeName = userTypeName;
    }

    private String userTypeName;
    private Integer userTypeCode;


    /**
     * 判断用户是否有权限
     * @param userTypes         权限校验注解上的用户类型
     * @param userTypeCode      登录用户的用户类型
     * @param logical           逻辑关系
     * @return
     */
    public static boolean hasPermission(UserTypeEnum[] userTypes,Integer userTypeCode,LogicalEnum logical){
        Objects.requireNonNull(userTypes,"当前方法缺少用户类型");
        Objects.requireNonNull(logical);

        if(userTypes.length == 1 && userTypes[0].getUserTypeCode().equals(userTypeCode)){
            return true;
        }

        if(userTypes.length > 1){
            switch(logical){
                case OR:
                    for (UserTypeEnum userType : userTypes) {
                        if(userType.getUserTypeCode().equals(userTypeCode)){
                            return true;
                        }
                    }
                    break;
                default: return false;
            }
        }

        return false;
    }


    public String getUserTypeName() {
        return userTypeName;
    }

    public Integer getUserTypeCode() {
        return userTypeCode;
    }
}
