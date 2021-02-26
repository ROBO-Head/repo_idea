package com.lagou.domain;

import java.util.List;

public class RoleResourceVO {

    // Role角色id
    private Integer roleId;
    // Resource资源分类id
    private Integer categoryId;

    // 资源id集合
    private List<Integer> resourceIdList;

    public List<Integer> getResourceIdList() {
        return resourceIdList;
    }

    public void setResourceIdList(List<Integer> resourceIdList) {
        this.resourceIdList = resourceIdList;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
