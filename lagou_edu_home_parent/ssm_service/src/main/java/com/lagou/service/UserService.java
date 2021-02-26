package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVO;

import java.util.List;

public interface UserService {

    // 用户分页&多条件组合查询
    PageInfo<User> findAllUserByPage(UserVO userVO);

    // 用户状态设置
    void updateUserStatus(Integer id, String status);

    // 用户登录
    User login(User user) throws Exception;

    // 根据用户id查询关联的角色信息
    List<Role> findUserRoleById(Integer id);

    // 分配角色
    void userContextRole(UserVO userVO);

    // 获取用户权限, 进行动态菜单展示
    ResponseResult getUserPermissions(Integer userId);

}
