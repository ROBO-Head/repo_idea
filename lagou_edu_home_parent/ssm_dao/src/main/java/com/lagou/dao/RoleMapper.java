package com.lagou.dao;

import com.lagou.domain.*;

import java.util.List;

public interface RoleMapper {

    // 查询所有&条件查询角色
    List<Role> findAllRole(Role role);

    // 添加角色
    void saveRole(Role role);

    // 修改角色
    void updateRole(Role role);

    // 根据角色id查询关联的菜单id
    List<Integer> findMenuByRoleId(Integer roleId);

    // 根据roleId清空中间表对应的关联关系
    void deleteRoleContextMenu(Integer rid);

    // 为角色分配菜单信息
    void roleContextMenu(Role_menu_relation role_menu_relation);

    // 删除角色
    void deleteRole(Integer roleId);

    // 根据角色id查询拥有的资源分类id的集合
    List<Integer> findResourceCategoryIdsByRoleId(Integer roleId);

    // 根据资源分类id查询资源分类信息
    ResourceCategory findResourceCategoryByCid(Integer cid);

    // 根据角色id和资源分类id多条件查询对应的资源信息
    List<Resource> findResourcesByRidAndCid(RoleResourceVO roleResourceVO);

    // 根据角色id删除与资源的关联关系(中间表)
    void deleteRoleContextResource(Integer roleId);

    // 为角色添加与资源的关联关系
    void roleContextResource(RoleResourceRelation roleResourceRelation);
}
