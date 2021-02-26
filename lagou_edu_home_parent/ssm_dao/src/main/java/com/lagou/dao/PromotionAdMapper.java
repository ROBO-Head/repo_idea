package com.lagou.dao;

import com.lagou.domain.PromotionAd;

import java.util.List;

public interface PromotionAdMapper {

    // 分页查询广告
    List<PromotionAd> findAllPromotionAdByPage();

    // 新建广告信息
    void savePromotionAd(PromotionAd promotionAd);

    // 修改广告信息
    void updatePromotionAd(PromotionAd promotionAd);

    // 回显广告信息(根据id查询)
    PromotionAd findPromotionAdById(Integer id);

    // 广告动态上下线
    void updatePromotionAdStatus(PromotionAd promotionAd);
}
