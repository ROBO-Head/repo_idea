package com.lagou.dao;

import com.lagou.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    // 用户分页&多条件组合查询
    List<User> findAllUserByPage(UserVO userVO);

    // 用户状态设置
    void updateUserStatus(@Param("id") Integer id, @Param("status") String status);

    // 用户登录
    User login(User user);

    // 根据用户id查询关联的角色信息
    List<Role> findUserRoleById(Integer id);

    // 根据用户id清空中间表
    void deleteUserContextRole(Integer userId);

    // 分配角色(向中间表添加记录)
    void userContextRole(User_Role_relation user_role_relation);

    // 根据角色id查询角色拥有的父级菜单(parent_id = -1)
    List<Menu> findParentMenuByRoleId(List<Integer> ids);

    // 对父菜单查询子菜单
    List<Menu> findSubMenuByPid(Integer pid);

    // 获取用户拥有的资源权限信息(根据角色信息获取)
    List<Resource> findResourceByRoleId(List<Integer> ids);


}
