package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourceVO;

import java.util.List;

public interface ResourceService {

    // 资源分页&多条件查询
    PageInfo<Resource> findAllResourceByPage(ResourceVO resourceVO);
}
