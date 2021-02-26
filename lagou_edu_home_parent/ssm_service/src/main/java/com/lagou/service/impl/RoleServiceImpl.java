package com.lagou.service.impl;

import com.lagou.dao.RoleMapper;
import com.lagou.domain.*;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAllRole(Role role) {
        return roleMapper.findAllRole(role);
    }

    @Override
    public List<Integer> findMenuByRoleId(Integer roleId) {
        return roleMapper.findMenuByRoleId(roleId);
    }

    @Override
    public void roleContextMenu(RoleMenuVO roleMenuVO) {
        // 根据roleId清空中间表对应的关联关系
        roleMapper.deleteRoleContextMenu(roleMenuVO.getRoleId());

        // 为角色分配菜单信息
        // 遍历menuIdList, 封装数据, 调用Mapper
        for (Integer mid : roleMenuVO.getMenuIdList()) {
            Role_menu_relation role_menu_relation = new Role_menu_relation();
            Date date = new Date();
            role_menu_relation.setRoleId(roleMenuVO.getRoleId());
            role_menu_relation.setMenuId(mid);
            role_menu_relation.setCreatedTime(date);
            role_menu_relation.setUpdatedTime(date);
            role_menu_relation.setCreatedBy("system");
            role_menu_relation.setUpdatedby("system");

            roleMapper.roleContextMenu(role_menu_relation);
        }
    }

    @Override
    public void deleteRole(Integer roleId) {
        // 删除关联关系中间表
        roleMapper.deleteRoleContextMenu(roleId);
        // 删除角色
        roleMapper.deleteRole(roleId);
    }

    @Override
    public void saveRole(Role role) {
        // 补全信息: 添加&修改时间, 创建&修改人
        Date date = new Date();
        role.setCreatedTime(date);
        role.setUpdatedTime(date);
        role.setCreatedBy("system");
        role.setUpdatedBy("system");
        // 调用mapper
        roleMapper.saveRole(role);
    }

    @Override
    public void updateRole(Role role) {
        // 补全信息: 修改时间, 修改人
        role.setUpdatedTime(new Date());
        role.setUpdatedBy("system");
        roleMapper.updateRole(role);
    }

    // 根据角色id查询拥有的资源信息
    @Override
    public List<ResourceCategory> findResourceListByRoleId(Integer roleId) {

        // 1.新建资源分类对象集合以及RoleResourceVO对象
        List<ResourceCategory> resourceCategoryList = new ArrayList<>();
        RoleResourceVO roleResourceVO = new RoleResourceVO();

        // 2.查询当前角色拥有的资源分类id集合
        List<Integer> resourceCategoryIds = roleMapper.findResourceCategoryIdsByRoleId(roleId);

        // 3.遍历resourceCategoryIds
        for (Integer cid : resourceCategoryIds) {
            // 根据每个id查询出对应的资源分类信息
            ResourceCategory resourceCategory = roleMapper.findResourceCategoryByCid(cid);

            // 根据角色id和资源分类id封装RoleResourceVO对象，查询出角色拥有的资源集合
            roleResourceVO.setRoleId(roleId);
            roleResourceVO.setCategoryId(cid);
            List<Resource> resourceList = roleMapper.findResourcesByRidAndCid(roleResourceVO);

            // 将resourceList封装进resourceCategory中
            resourceCategory.setResourceList(resourceList);

            // 将resourceCategory对象放入resourceCategoryList中
            resourceCategoryList.add(resourceCategory);
        }

        // 4.返回结果
        return resourceCategoryList;
    }

    @Override
    public void roleContextResource(RoleResourceVO roleResourceVO) {
        // 根据角色id删除与资源的关联关系(中间表)
        roleMapper.deleteRoleContextResource(roleResourceVO.getRoleId());

        // 为角色分配资源, 遍历资源id集合, 封装数据
        Date date = new Date();
        RoleResourceRelation roleResourceRelation = new RoleResourceRelation();
        for (Integer resourceId : roleResourceVO.getResourceIdList()) {

            roleResourceRelation.setResourceId(resourceId);
            roleResourceRelation.setRoleId(roleResourceVO.getRoleId());

            roleResourceRelation.setCreatedTime(date);
            roleResourceRelation.setUpdatedTime(date);
            roleResourceRelation.setCreatedBy("system");
            roleResourceRelation.setUpdatedBy("system");

            // 调用mapper
            roleMapper.roleContextResource(roleResourceRelation);
        }

    }


}
