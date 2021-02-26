package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {

    @Autowired
    private PromotionAdService promotionAdService;

    // 广告分页查询
    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllPromotionAdByPage(PromotionAdVO promotionAdVO) {
        PageInfo<PromotionAd> pageInfo = promotionAdService.findAllPromotionAdByPage(promotionAdVO);
        return new ResponseResult(true, 200, "分页查询成功", pageInfo);
    }

    // 图片上传
    @RequestMapping("/PromotionAdUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        try {
            // 1.判断文件是否为空, 是则抛出异常
            if (file.isEmpty()) {
                throw new RuntimeException();
            }

            // 2.获取原文件名
            String originalFilename = file.getOriginalFilename();

            // 3.创建新文件名, 使用时间戳 + 文件后缀名拼接
            String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

            // 4.获取项目部署路径: /Users/gengxin/Tomcat/apache-tomcat-8.5.59/webapps/
            String realPath = request.getServletContext().getRealPath("/");
            String webappsPath = realPath.substring(0, realPath.indexOf("ssm_web"));

            // 5.上传文件
            // 文件上传路径
            String uploadPath = webappsPath + "upload/";
            // 创建文件(路径 + 新文件名)
            File filePath = new File(uploadPath, newFileName);
            // 如果上传路径不存在则新建
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
                System.out.println("创建目录: " + filePath);
            }
            // 上传
            file.transferTo(filePath);

            // 6.将文件名和文件路径封装到map中, 响应给前端
            Map<String, String> map = new HashMap<>();
            map.put("fileName", newFileName);
            map.put("filePath", "http://localhost:8080/upload/" + newFileName);
            // 返回ResponseResult
            return new ResponseResult(true, 200, "上传成功", map);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 新建&修改广告信息
    @RequestMapping("/saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd) {

        // 判断是否传入了id
        if (null == promotionAd.getId()) {
            // 新建操作
            promotionAdService.savePromotionAd(promotionAd);
            return new ResponseResult(true, 200, "新建广告成功", null);
        } else {
            // 修改操作
            promotionAdService.updatePromotionAd(promotionAd);
            return new ResponseResult(true, 200, "修改广告信息修改", null);
        }
    }

    // 回显广告信息(根据id查询)
    @RequestMapping("/findPromotionAdById")
    public ResponseResult findPromotionAdById(Integer id) {
        PromotionAd promotionAd = promotionAdService.findPromotionAdById(id);
        return new ResponseResult(true, 200, "回显广告信息成功", promotionAd);
    }

    // 广告动态上下线
    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updatePromotionAdStatus(Integer id, Integer status) {
        promotionAdService.updatePromotionAdStatus(id, status);
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        return new ResponseResult(true, 200, "广告状态修改成功", map);
    }

}
