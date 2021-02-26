package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo<User> findAllUserByPage(UserVO userVO) {
        // 设置分页参数
        PageHelper.startPage(userVO.getCurrentPage(), userVO.getPageSize());
        // 调用mapper
        List<User> list = userMapper.findAllUserByPage(userVO);
        return new PageInfo<>(list);
    }

    @Override
    public void updateUserStatus(Integer id, String status) {
        userMapper.updateUserStatus(id, status);
    }

    @Override
    public User login(User user) throws Exception {

        // 调用mapper查询用户, 判断查询结果是否为空, 以及查询结果的密码是否匹配前端传递的明文密码的加密结果
        User user1 = userMapper.login(user);
        if (user1 != null && Md5.verify(user.getPassword(), "lagou", user1.getPassword())) {
            // 验证通过, 返回查询结果
            return user1;
        } else {
            // 查询结果为空或密码不匹配
            return null;
        }
    }

    @Override
    public List<Role> findUserRoleById(Integer id) {
        return userMapper.findUserRoleById(id);
    }

    @Override
    public void userContextRole(UserVO userVO) {
        // 1.根据用户id清空中间表
        userMapper.deleteUserContextRole(userVO.getUserId());

        // 2.分配角色
        Date date = new Date();
        for (Integer roleId : userVO.getRoleIdList()) {
            // 封装数据
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setUserId(userVO.getUserId());
            user_role_relation.setRoleId(roleId);
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);
            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedBy("system");

            userMapper.userContextRole(user_role_relation);
        }
    }

    // 获取用户权限, 进行动态菜单展示
    @Override
    public ResponseResult getUserPermissions(Integer userId) {
        // 1.根据用户id查询拥有的角色
        List<Role> roleList = userMapper.findUserRoleById(userId);
        // 2.获取角色id保存到list集合
        List<Integer> roleIds = new ArrayList<>();
        for (Role role : roleList) {
            roleIds.add(role.getId());
        }
        // 3.根据角色id查询父菜单
        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(roleIds);
        // 4.查询父菜单关联的子菜单, 封装到父菜单对象中
        for (Menu menu : parentMenu) {
            List<Menu> subMenu = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(subMenu);
        }
        // 5.获取资源信息
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIds);
        // 6.封装数据并返回
        Map<String, Object> map = new HashMap<>();
        map.put("menuList", parentMenu);
        map.put("resourceList", resourceList);

        return new ResponseResult(true, 200, "获取用户权限信息成功", map);
    }
}
