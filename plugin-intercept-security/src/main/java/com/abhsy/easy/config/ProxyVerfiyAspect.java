package com.abhsy.easy.config;

import com.abhsy.easy.adapt.VerifySecurity;
import com.abhsy.easy.constant.ConstantKey;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.abhsy.easy.constant.ErrorCode;
import com.abhsy.easy.feign.ISecurityServiceBiz;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-29
 **/
@Aspect
@Component
@Slf4j
public class ProxyVerfiyAspect {

    @Autowired
    ISecurityServiceBiz iSecurityServiceBiz;

    @Autowired
    ThreadLocal<Result> threadLocal;

    @Autowired
    ObjectMapper objectMapper;

    @Around("@annotation(verifySecurity)")
    public void verfiyControl(ProceedingJoinPoint point, VerifySecurity verifySecurity) throws Throwable {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            HttpServletResponse response = requestAttributes.getResponse();
            String uri = request.getRequestURI().toString();
            if(log.isInfoEnabled())log.info("[verify uri is]===" + uri);
            response.setContentType("application/json;charset=UTF-8");
            Result result = iSecurityServiceBiz.securityUri(request.getHeader(ConstantKey.TOKENHEADER),uri);
            if(log.isInfoEnabled())log.info("[return result]===" + result != null ? result.getMsg() : "result is null");
            threadLocal.set(result);
            if(ErrorCode.SUCESS == result.getCode()){
                response.getWriter().print(objectMapper.writeValueAsString(point.proceed()));
                threadLocal.remove();
            }else{
                response.getWriter().print(JSON.toJSON(result));
            }
    }
}












































