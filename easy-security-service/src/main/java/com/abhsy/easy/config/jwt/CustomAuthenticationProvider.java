package com.abhsy.easy.config.jwt;

import com.abhsy.easy.config.UserPassInfoAuthenticationToken;
import com.alibaba.fastjson.JSON;
import com.abhsy.easy.vo.BasicUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

/**
 * 自定义身份认证验证组件
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final ThreadLocal<String> context = new ThreadLocal<>();

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        if (null != userDetails) {
            if (bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
                // 这里设置权限和角色
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                context.set(JSON.toJSONString((BasicUser) userDetails));
                Authentication auth = new UserPassInfoAuthenticationToken(name, password, authorities,context);
                return auth;
            } else {
                throw new BadCredentialsException("密码错误");
            }
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }

    /**
     * 是否可以提供输入类型的认证服务
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
