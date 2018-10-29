package com.abhsy.easy.controller;

import com.abhsy.easy.vo.Result;
import com.abhsy.easy.constant.ErrorCode;
import com.abhsy.easy.controller.biz.BaseController;
import com.abhsy.easy.util.CodeUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-26
 **/
@RestController
@Slf4j
public class VerifyUri extends BaseController{

    @Autowired
    ThreadLocal<String> threadLocal;

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/test1")
    public String test1() {
        return "test1";
    }

    @RequestMapping("/verifyUri")
    public Result verifyUri() {
        return new Result(ErrorCode.SUCESS, threadLocal.get());
    }

    @RequestMapping("/users/getCode")
    @ApiOperation(value = "获得验证码", notes = "")
    public void getCode(HttpServletRequest request ,HttpServletResponse response) throws Exception {
        Map<String, Object> codeMap = CodeUtil.generateCodeAndPic();
        String imageCode = codeMap.get("code").toString().toUpperCase();
        String cookieValue = URLEncoder.encode(bCryptPasswordEncoder.encode(imageCode), "utf-8");
        Cookie cookie = new Cookie("bmp_code", cookieValue);

//        if (null != request) {// 设置域名的cookie
//            String domainName = getDomainName(request);
//            System.out.println(domainName);
//            if (!"localhost".equals(domainName)) {
//                cookie.setDomain(domainName);
//            }
//        }
        cookie.setPath("/");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", -1);
        response.addCookie(cookie);
        response.setContentType("image/jpeg");
        ServletOutputStream sos;
        try {
            sos = response.getOutputStream();
            ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
            sos.close();
            log.info("生成二维码成功：code：" + codeMap.get("code").toString());
        } catch (IOException e) {
            log.info("生成验证码失败");
            e.printStackTrace();
        }
    }

}
