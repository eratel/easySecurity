package com.abhsy.easy.config;

import com.abhsy.easy.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.abhsy.easy.bean.User;
import com.abhsy.easy.config.jwt.CustomAuthenticationProvider;
import com.abhsy.easy.config.jwt.JWTAuthenticationFilter;
import com.abhsy.easy.config.jwt.JWTLoginFilter;
import com.abhsy.easy.constant.ConstantKey;
import com.abhsy.easy.constant.ErrorCode;
import com.abhsy.easy.constant.NoFilter;
import com.abhsy.easy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;
    @Autowired
    UrlFilterSecurity urlFilterSecurity;
    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired
    AuthenticationDeniedHandler authenticationDeniedHandler;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(new CustomAuthenticationProvider(userService, new BCryptPasswordEncoder()));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(ConstantKey.ERRORPAGE).antMatchers(ConstantKey.LOGINPAGE)
                .antMatchers(NoFilter.AUTH_WHITELIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(urlFilterSecurity);
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }
                    //loginPage is failed to redirect / uri
                }).and().formLogin().loginPage(ConstantKey.ERRORPAGE).loginProcessingUrl("/login").usernameParameter(ConstantKey.USERNAME).passwordParameter(ConstantKey.PASSWORD).permitAll().failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                httpServletResponse.setContentType(ConstantKey.CONTENTTYPE);
                PrintWriter out = httpServletResponse.getWriter();
                Result<Object> objectResult = new Result<>();
                objectResult.setCode(ErrorCode.AUTH_ERROR);
                if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                    objectResult.setMsg("用户名或密码输入错误，登录失败!");
                } else if (e instanceof DisabledException) {
                    objectResult.setMsg("账户被禁用，登录失败，请联系管理员!");
                } else {
                    objectResult.setMsg("登录失败!");
                }
                out.write(objectMapper.writeValueAsString(objectResult));
                out.flush();
                out.close();
            }
        }).successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter out = httpServletResponse.getWriter();
                ObjectMapper objectMapper = new ObjectMapper();
                out.write(objectMapper.writeValueAsString(Result.instance(ErrorCode.SUCESS,objectMapper.writeValueAsString(getCurrentRole()))));
                out.flush();
                out.close();
            }
        }).and().logout().permitAll().and().csrf().disable().exceptionHandling().accessDeniedHandler(authenticationDeniedHandler).and()
                .addFilter(new JWTLoginFilter(authenticationManager()))
                //验证token
                .addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }

    public User getCurrentRole() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

