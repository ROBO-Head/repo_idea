package com.lagou.controller;

import com.lagou.domain.Menu;
import com.lagou.domain.ResponseResult;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // 查询菜单列表
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu() {
        List<Menu> list = menuService.findAllMenu();
        return new ResponseResult(true, 200, "查询菜单列表成功", list);
    }

    // 回显菜单信息
    @RequestMapping("/findMenuInfoById")
    public ResponseResult findMenuInfoById(Integer id) {

        // 根据id的值判断添加还是修改操作
        if (-1 == id) {
            // 添加操作
            List<Menu> list = menuService.findSubMenuListByPid(-1);
            Map<String, Object> map = new HashMap<>();
            map.put("menuInfo", null);
            map.put("parentMenuList", list);
            return new ResponseResult(true, 200, "添加回显成功", map);

        } else {
            // 修改操作, 需要回显当前menu信息
            Menu menu = menuService.findMenuById(id);
            List<Menu> list = menuService.findSubMenuListByPid(-1);
            Map<String, Object> map = new HashMap<>();
            map.put("menuInfo", menu);
            map.put("parentMenuList", list);
            return new ResponseResult(true, 200, "修改回显成功", map);
        }
    }

    // 添加&修改菜单
    @RequestMapping("/saveOrUpdateMenu")
    public ResponseResult saveOrUpdateMenu(@RequestBody Menu menu) {

        // 判断是否传入id值
        if (null == menu.getId()) {
            // 添加操作
            menuService.saveMenu(menu);
            return new ResponseResult(true, 200, "添加菜单成功", null);
        } else {
            // 修改菜单
            menuService.updateMenu(menu);
            return new ResponseResult(true, 200, "修改菜单成功", null);
        }
    }
}
