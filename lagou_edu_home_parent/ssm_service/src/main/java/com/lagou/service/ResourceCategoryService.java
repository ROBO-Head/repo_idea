package com.lagou.service;

import com.lagou.domain.ResourceCategory;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ResourceCategoryService {

    // 查询所有资源分类
    List<ResourceCategory> findAllResourceCategory();

    // 添加资源分类信息
    void saveResourceCategory(ResourceCategory resourceCategory);

    // 修改资源分类信息
    void updateResourceCategory(ResourceCategory resourceCategory);

    // 删除资源分类信息
    void deleteResourceCategory(Integer id);

}
