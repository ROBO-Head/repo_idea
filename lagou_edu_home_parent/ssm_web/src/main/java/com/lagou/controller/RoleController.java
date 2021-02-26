package com.lagou.controller;

import com.lagou.domain.*;
import com.lagou.service.MenuService;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @RequestMapping("/findAllRole")
    public ResponseResult findAllRole(@RequestBody Role role) {
        List<Role> list = roleService.findAllRole(role);
        return new ResponseResult(true, 200, "查询所有角色成功", list);
    }

    // 查询所有父子菜单
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu() {

        // 所有父级菜单的parent_id都是-1
        List<Menu> list = menuService.findSubMenuListByPid(-1);

        Map<String, Object> map = new HashMap<>();
        map.put("parentMenuList", list);
        return new ResponseResult(true, 200, "查询所有父子菜单成功", map);
    }

    // 根据角色id查询关联的菜单id
    @RequestMapping("/findMenuByRoleId")
    public ResponseResult findMenuByRoleId(Integer roleId) {
        List<Integer> list = roleService.findMenuByRoleId(roleId);
        return new ResponseResult(true, 200, "查询角色关联的菜单信息成功", list);
    }

    // 为角色分配菜单信息
    @RequestMapping("/RoleContextMenu")
    public ResponseResult roleContextMenu(@RequestBody RoleMenuVO roleMenuVO) {
        roleService.roleContextMenu(roleMenuVO);
        return new ResponseResult(true, 200, "响应成功", null);
    }

    // 删除角色
    @RequestMapping("/deleteRole")
    public ResponseResult deleteRole(Integer id) {
        roleService.deleteRole(id);
        return new ResponseResult(true, 200, "删除角色成功", null);
    }

    // 添加&修改角色
    @RequestMapping("/saveOrUpdateRole")
    public ResponseResult saveOrUpdateRole(@RequestBody Role role) {

        // 判断是否传入id
        if (null == role.getId()) {
            // 添加操作
            roleService.saveRole(role);
            return new ResponseResult(true, 200, "添加角色成功", null);
        } else {
            // 修改操作
            roleService.updateRole(role);
            return new ResponseResult(true, 200, "修改角色成功", null);
        }
    }

    // 根据角色id查询拥有的资源信息
    @RequestMapping("/findResourceListByRoleId")
    public ResponseResult findResourceListByRoleId(Integer roleId) {
        // 调用service
        List<ResourceCategory> list = roleService.findResourceListByRoleId(roleId);
        // 返回结果
        return new ResponseResult(true, 200, "查询角色拥有的资源信息成功", list);
    }

    // 为角色分配资源
    @RequestMapping("/roleContextResource")
    public ResponseResult roleContextResource(@RequestBody RoleResourceVO roleResourceVO) {
        roleService.roleContextResource(roleResourceVO);
        return new ResponseResult(true, 200, "为角色分配资源成功", null);
    }

}
