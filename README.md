# EasySecurity
----------------------------------------------------------------------------------------------------------------
更名ABHSY-eratel
基于JWT SPRINGSECUTIRY 轻量级鉴权中心
============================================

Quick Start
-----------

如果使用引用拦截包 :
### 
    <dependency>
        <groupId>com.abhsy.easy</groupId>
        <artifactId>plugin-securitya-common</artifactId>
        <version>1.0</version>
    </dependency>  
### 

只需要在你需要添加权限的方法中添加一个自定义注解，就能够完成权限的验证
### 
    @RequestMapping(value = "/add")
    @VerifySecurity
### 
