package com.abhsy.easy.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicUser {
    public Long id;
    public String name;
    public String phone;
    public String telephone;
    public String address;
    public boolean enabled;
    public String username;
    public String email;
    public String sex;
    public String province;
    public String city;
    public String district;
    public String enterprise_id;
}