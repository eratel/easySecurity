package com.abhsy.easy.config.jwt;

import com.abhsy.easy.config.UserPassInfoAuthenticationToken;
import com.abhsy.easy.constant.ConstantKey;
import com.abhsy.easy.controller.VerifyUri;
import com.abhsy.easy.vo.Result;
import com.alibaba.fastjson.JSON;
import com.abhsy.easy.constant.ErrorCode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-30
 **/
@Slf4j
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

    }

    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        String username = req.getParameter(ConstantKey.USERNAME);
        String password = req.getParameter(ConstantKey.PASSWORD);
        String requestUrl = req.getRequestURI();
        if(requestUrl.equals(ConstantKey.LOGINPAGE_URI)){
            if(! verflyImageCode(req, res)){
                return null;
            }
        }

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password,
                        new ArrayList<>())
        );
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        // builder the token
        String token = null;
        try {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            // 定义存放角色集合的对象
            StringBuffer buffer = new StringBuffer();
            for (GrantedAuthority grantedAuthority : authorities) {
                buffer.append(grantedAuthority.getAuthority()).append(",");
            }

            // 设置过期时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 30);
            Date time = calendar.getTime();
            UserPassInfoAuthenticationToken userPassInfoAuthenticationToken = (UserPassInfoAuthenticationToken) auth;
            token = Jwts.builder()
                    .setSubject(auth.getName() + "-" + buffer.toString() + "-" + userPassInfoAuthenticationToken.getThreadLocal().get())
                    .setExpiration(time)
                    .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY) //设置算法
                    .compact();
            response.addHeader(ConstantKey.TOKENHEADER, "Bearer " + token);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(JSON.toJSONString(Result.instance(0,"登陆成功")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Boolean verflyImageCode(HttpServletRequest req, HttpServletResponse res){
        String imageCode = req.getParameter("imageCode");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Cookie[] cookieList = req.getCookies();
        res.setContentType("application/json;charset=UTF-8");
        if (cookieList == null ) {
            try {
                res.getWriter().print(JSON.toJSONString(Result.instance(ErrorCode.INNER_ERROR, "请输入验证码")));
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
            return false;
        }
        String bmp_code = null;
        for (int i = 0; i < cookieList.length; i++) {
            if (cookieList[i].getName().equals("bmp_code")) {
                try {
                    bmp_code = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
        Cookie cookie = new Cookie("bmp_code", "");
        cookie.setMaxAge(-1);
        String domainName = VerifyUri.getDomainName(req);
        if (!"localhost".equals(domainName)) {
            cookie.setDomain(domainName);
        }
        cookie.setPath("/");
        res.addCookie(cookie);
        if(! bCryptPasswordEncoder.matches(imageCode.toUpperCase(),bmp_code)){
            try {
                res.getWriter().print(JSON.toJSONString(Result.instance(ErrorCode.INNER_ERROR, "验证码验证错误")));
                return false;
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
        return true;
    }
}