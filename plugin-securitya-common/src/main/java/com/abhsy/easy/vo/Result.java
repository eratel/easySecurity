package com.abhsy.easy.vo;

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
    private T restluLis = null;
    private Long total;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T restluLis) {
        this.code = code;
        this.msg = msg;
        this.restluLis = restluLis;
    }

    public static Result instance(int code, String msg) {
        return new Result(code, msg);
    }

}
