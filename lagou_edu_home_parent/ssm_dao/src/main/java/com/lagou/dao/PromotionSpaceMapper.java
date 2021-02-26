package com.lagou.dao;

import com.lagou.domain.PromotionSpace;

import java.util.List;

public interface PromotionSpaceMapper {

    // 获取所有广告位
    List<PromotionSpace> findAllPromotionSpace();

    // 添加广告位
    void savePromotionSpace(PromotionSpace promotionSpace);

    // 根据广告位id查询广告位信息(回显广告位名称)
    PromotionSpace findPromotionSpaceById(Integer id);

    // 修改广告位
    void updatePromotionSpace(PromotionSpace promotionSpace);

}
