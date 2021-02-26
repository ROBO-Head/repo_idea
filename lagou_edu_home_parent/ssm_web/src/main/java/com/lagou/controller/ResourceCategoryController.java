package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourceCategory;
import com.lagou.domain.ResponseResult;
import com.lagou.service.ResourceCategoryService;
import com.lagou.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ResourceCategory")
public class ResourceCategoryController {

    @Autowired
    private ResourceCategoryService resourceCategoryService;

    // 查询所有资源分类
    @RequestMapping("/findAllResourceCategory")
    public ResponseResult findAllResourceCategory() {
        List<ResourceCategory> list = resourceCategoryService.findAllResourceCategory();
        return new ResponseResult(true, 200, "查询所有资源分类成功", list);
    }

    // 添加&修改资源分类
    @RequestMapping("/saveOrUpdateResourceCategory")
    public ResponseResult saveOrUpdateResourceCategory(@RequestBody ResourceCategory resourceCategory) {
        // 判断是否携带id
        if (null == resourceCategory.getId()) {
            // 添加操作
            resourceCategoryService.saveResourceCategory(resourceCategory);
            return new ResponseResult(true, 200, "添加资源分类成功", null);
        } else {
            // 修改操作
            resourceCategoryService.updateResourceCategory(resourceCategory);
            return new ResponseResult(true, 200, "修改资源分类成功", null);
        }
    }

    // 删除资源分类信息
    @RequestMapping("/deleteResourceCategory")
    public ResponseResult deleteResourceCategory(Integer id) {
        resourceCategoryService.deleteResourceCategory(id);
        return new ResponseResult(true, 200, "删除资源分类成功", null);
    }
}
