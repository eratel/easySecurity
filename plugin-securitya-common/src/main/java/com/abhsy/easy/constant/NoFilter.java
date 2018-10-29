package com.abhsy.easy.constant;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-10
 **/
//配置不用通过权限验证的入口
public class NoFilter {

    public static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/users/getCode"
//            "/users/**",
//            "/roles/**",
//            "/menus/**"

    };
}
