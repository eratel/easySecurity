package com.abhsy.easy.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-10-09
 **/
public class UserPassInfoAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private ThreadLocal<String> threadLocal;

    public UserPassInfoAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public UserPassInfoAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public UserPassInfoAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities,ThreadLocal<String> threadLocal) {
        super(principal, credentials, authorities);
        this.threadLocal = threadLocal;
    }

    public ThreadLocal<String> getThreadLocal() {
        return threadLocal;
    }
}
