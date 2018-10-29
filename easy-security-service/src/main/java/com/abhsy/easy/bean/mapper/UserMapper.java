package com.abhsy.easy.bean.mapper;

import com.abhsy.easy.bean.User;
import com.abhsy.easy.vo.Role;
import org.apache.ibatis.annotations.*;

/**
 * @program: bmp
 * @author: jikai.sun
 * @create: 2018-09-27
 **/
public interface UserMapper {

    @Select("select * from user where enabled = 1 AND  username = #{name}")
    @Results({
            @Result(property = "roles", column = "id", many = @Many(select = "com.tg.bmp.bean.mapper.UserMapper.findRole"))
    })
    public User findByUsername(String name);

    @Select("SELECT\n" +
            "\t*\n" +
            "FROM\n" +
            "\trole\n" +
            "WHERE\n" +
            "\tid IN (\n" +
            "\t\tSELECT\n" +
            "\t\t\tROLEID\n" +
            "\t\tFROM\n" +
            "\t\t\tuser_of_role\n" +
            "\t\tWHERE\n" +
            "\t\t\tUSERID = #{uid}\n" +
            "\t)")
    public Role findRole(String uid);
}














