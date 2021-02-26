package com.lagou.service;

import com.lagou.domain.Menu;

import java.util.List;

public interface MenuService {

    // 查询所有父子菜单
    List<Menu> findSubMenuListByPid(Integer pid);

    // 查询菜单列表
    List<Menu> findAllMenu();

    // 根据id查询菜单信息
    Menu findMenuById(Integer id);

    // 添加菜单
    void saveMenu(Menu menu);

    // 修改菜单
    void updateMenu(Menu menu);
}
