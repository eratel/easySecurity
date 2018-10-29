package com.abhsy.easy.constant;


public interface ConstantKey {

    /**
     * 签名key
     */
     String SIGNING_KEY = "spring-security-@Jwt!&Secret^#";

     String CONTENTTYPE = "application/json;charset=utf-8";

//     String SUCCESS = "success";

     String ERROR = "error";

     String TOKENHEADER = "Authorization";

     String ROLELOGIN = "ROLE_LOGIN";

     String ERRORPAGE = "/login_error";

     String LOGINPAGE = "login";

     String LOGINPAGE_URI = "/auth/login";

     String USERNAME = "n";

     String PASSWORD = "p";

    String [] NOFILTERURI = {
        "/login",
        "/error"
     };
}
