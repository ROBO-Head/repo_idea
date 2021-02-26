package com.lagou.service;

import com.lagou.domain.*;

import java.util.List;

public interface RoleService {

    // 查询所有&条件查询角色
    List<Role> findAllRole(Role role);

    // 根据角色id查询关联的菜单id
    List<Integer> findMenuByRoleId(Integer roleId);

    // 为角色分配菜单
    void roleContextMenu(RoleMenuVO roleMenuVO);

    // 删除角色
    void deleteRole(Integer roleId);

    // 添加角色
    void saveRole(Role role);

    // 修改角色
    void updateRole(Role role);

    // 根据角色id查询拥有的资源信息
    List<ResourceCategory> findResourceListByRoleId(Integer roleId);

    // 为角色分配资源
    void roleContextResource(RoleResourceVO roleResourceVO);
}
