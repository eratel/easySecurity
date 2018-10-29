package com.abhsy.easy.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-29
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private int code = 0;
    public String msg;

    public static Result instance(int code, String msg) {
        return new Result(code,msg);
    }

}
