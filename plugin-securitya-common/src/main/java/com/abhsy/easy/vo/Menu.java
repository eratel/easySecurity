package com.abhsy.easy.vo;

import lombok.Data;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-26
 **/
@Data
public class Menu {
    private Long id;
    private String url;
    private String name;
    private String iconCls;
    private Long parentId;
    private List<Role> roles;
    private List<Menu> children;
    private String enabled;
}
