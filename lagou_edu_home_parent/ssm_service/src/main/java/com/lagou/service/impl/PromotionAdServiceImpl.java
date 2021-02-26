package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.PromotionAdMapper;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PromotionAdServiceImpl implements PromotionAdService {

    @Autowired
    private PromotionAdMapper promotionAdMapper;

    @Override
    public PageInfo<PromotionAd> findAllPromotionAdByPage(PromotionAdVO promotionAdVO) {

        // 使用分页助手进行分页, 传入分页参数(当前页, 每页条数)
        PageHelper.startPage(promotionAdVO.getCurrentPage(), promotionAdVO.getPageSize());
        // 调用dao
        List<PromotionAd> list = promotionAdMapper.findAllPromotionAdByPage();
        // 将查询结果放入PageInfo对象进行返回
        return new PageInfo<>(list);
    }

    @Override
    public void savePromotionAd(PromotionAd promotionAd) {
        // 补全信息: 创建&修改时间
        Date date = new Date();
        promotionAd.setCreateTime(date);
        promotionAd.setUpdateTime(date);
        promotionAdMapper.savePromotionAd(promotionAd);
    }

    @Override
    public void updatePromotionAd(PromotionAd promotionAd) {
        // 补全信息: 修改时间
        promotionAd.setUpdateTime(new Date());
        promotionAdMapper.updatePromotionAd(promotionAd);
    }

    @Override
    public PromotionAd findPromotionAdById(Integer id) {
        return promotionAdMapper.findPromotionAdById(id);
    }

    @Override
    public void updatePromotionAdStatus(Integer id, Integer status) {
        // 封装对象
        PromotionAd promotionAd = new PromotionAd();
        promotionAd.setStatus(status);
        promotionAd.setId(id);
        promotionAd.setUpdateTime(new Date());

        promotionAdMapper.updatePromotionAdStatus(promotionAd);
    }
}
