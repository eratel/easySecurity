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
public class Role {
    private Long id;
    private String name;
    private String nameZh;
}
