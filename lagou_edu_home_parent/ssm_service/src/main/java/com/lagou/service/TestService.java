package com.lagou.service;

import com.lagou.domain.Test;

import java.util.List;

/**
 * 对test表查询所有
 */
public interface TestService {

    List<Test> findAllTest();
}
