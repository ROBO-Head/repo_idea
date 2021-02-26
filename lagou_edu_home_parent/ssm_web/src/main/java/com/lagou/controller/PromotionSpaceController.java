package com.lagou.controller;

import com.lagou.domain.PromotionSpace;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/PromotionSpace")
public class PromotionSpaceController {

    @Autowired
    private PromotionSpaceService promotionSpaceService;

    // 查询所有广告位
    @RequestMapping("/findAllPromotionSpace")
    public ResponseResult findAllPromotionSpace() {
        List<PromotionSpace> list = promotionSpaceService.findAllPromotionSpace();
        return new ResponseResult(true, 200, "查询广告位成功", list);
    }

    // 添加&修改广告位
    @RequestMapping("/saveOrUpdatePromotionSpace")
    public ResponseResult saveOrUpdatePromotionSpace(@RequestBody PromotionSpace promotionSpace) {

        if (null == promotionSpace.getId()) {
            // 新增操作
            promotionSpaceService.savePromotionSpace(promotionSpace);
            return new ResponseResult(true, 200, "添加广告位成功", null);
        } else {
            // 修改操作
            promotionSpaceService.updatePromotionSpace(promotionSpace);
            return new ResponseResult(true, 200, "修改广告位成功", null);
        }
    }

    // 根据广告位id查询广告位信息(回显广告位名称)
    @RequestMapping("/findPromotionSpaceById")
    public ResponseResult findPromotionSpaceById(Integer id) {
        PromotionSpace promotionSpace = promotionSpaceService.findPromotionSpaceById(id);
        return new ResponseResult(true, 200, "回显广告位信息成功", promotionSpace);
    }
}
