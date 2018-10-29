package com.abhsy.easy.service;


import com.abhsy.easy.bean.mapper.MenuMapper;
import com.abhsy.easy.vo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    public List<Menu> getAllMenu() {
        return menuMapper.findAll();
    }

}
