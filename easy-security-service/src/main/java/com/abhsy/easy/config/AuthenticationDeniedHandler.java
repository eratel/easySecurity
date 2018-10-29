package com.abhsy.easy.config;

import com.abhsy.easy.vo.Result;
import com.alibaba.fastjson.JSON;
import com.abhsy.easy.constant.ErrorCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-26
 **/
@Component
public class AuthenticationDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse resp, AccessDeniedException e) throws IOException, ServletException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write(JSON.toJSON(new Result(ErrorCode.SUCESS, "权限不足，请联系管理员!")).toString());
        out.flush();
        out.close();
    }
}
