package com.abhsy.easy.config;


import com.abhsy.easy.constant.ConstantKey;
import com.abhsy.easy.service.MenuService;
import com.abhsy.easy.vo.Menu;
import com.abhsy.easy.vo.Role;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-26
 **/
@Component
@Slf4j
public class UrlFilterSecurity implements FilterInvocationSecurityMetadataSource {

    @Autowired
    MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    ThreadLocal<String> threadLocal;

    private final String VERFIYURI = "/verifyUri";

    /**
     * 拿到当前请求的有权限访问的角色有哪些
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        String requestUrl = ((FilterInvocation) o).getRequestUrl();

        for (String s :
                ConstantKey.NOFILTERURI) {
            if (s.equals(requestUrl)) return null;
        }

        if (VERFIYURI.equals(requestUrl)) {
            requestUrl = ((FilterInvocation) o).getRequest().getHeader("uri");
            if(log.isInfoEnabled())log.info("[Auth Security uri is]" + requestUrl);
        }
        try {
            String principal = (String) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            if (StringUtils.isNotBlank(principal) && ! "anonymousUser".equals(principal)) {
                if(log.isInfoEnabled())log.info("[Auth Security principal]" + principal);
                String s = principal.split("-")[2];
                threadLocal.set(s);
            }
        } catch (Exception e) {
            log.error("找不到用户信息", e);
        }
        List<Menu> allMenu = menuService.getAllMenu();
        for (Menu menu : allMenu) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl) && menu.getRoles().size() > 0) {
                List<Role> roles = menu.getRoles();
                int size = roles.size();
                String[] values = new String[size];
                for (int i = 0; i < size; i++) {
                    values[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(values);
            }
        }

        return SecurityConfig.createList(ConstantKey.ROLELOGIN);

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
