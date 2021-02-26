package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;

import java.util.List;

public interface PromotionAdService {

    // 分页查询
    PageInfo<PromotionAd> findAllPromotionAdByPage(PromotionAdVO promotionAdVO);

    // 新建广告信息
    void savePromotionAd(PromotionAd promotionAd);

    // 修改广告信息
    void updatePromotionAd(PromotionAd promotionAd);

    // 回显广告信息(根据id查询)
    PromotionAd findPromotionAdById(Integer id);

    // 广告动态上下线
    void updatePromotionAdStatus(Integer id, Integer status);
}
