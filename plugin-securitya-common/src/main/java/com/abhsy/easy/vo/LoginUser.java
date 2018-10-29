package com.abhsy.easy.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser extends BasicUser {

    @JSONField(serialize = false)
    private String password;

    @JSONField(serialize = false)
    private List<Long> roleIds;

    private List<Role> roles;

//    private Boolean isChangRoleIds;
}