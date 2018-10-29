package com.abhsy.easy.bean.mapper;


import com.abhsy.easy.vo.Menu;
import com.abhsy.easy.vo.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-27
 **/
public interface MenuMapper {

    @Select("SELECT * FROM menu")
    @Results({
            @Result(property = "roles", column = "id", many = @Many(select = "com.tg.bmp.bean.mapper.MenuMapper.findRole"))
    })
    public List<Menu> findAll();

    @Select("SELECT\n" +
            "\t*\n" +
            "FROM\n" +
            "\trole\n" +
            "WHERE\n" +
            "\tid IN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tROLEID\n" +
            "\t\tFROM\n" +
            "\t\t\trole_of_menu\n" +
            "\t\tWHERE\n" +
            "\t\t\tMENUID = #{mid}\n" +
            "\t)")
    public List<Role> findRole(String mid);

}























