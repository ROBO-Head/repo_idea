package com.lagou.dao;

import com.lagou.domain.Test;

import java.util.List;

/**
 * 测试对test表查询所有
 */
public interface TestMapper {

    List<Test> findAllTest();
}
