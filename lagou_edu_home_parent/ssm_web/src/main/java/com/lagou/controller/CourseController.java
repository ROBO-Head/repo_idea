package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // 多条件课程列表查询
    @RequestMapping("/findCourseByCondition")
    public ResponseResult findCourseByCondition(@RequestBody CourseVO courseVO) {
        List<Course> list = courseService.findCourseByCondition(courseVO);
        return new ResponseResult(true, 200, "响应成功", list);
    }

    // 文件上传
    @RequestMapping("/courseUpload")
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

    // 新增&修改课程及讲师信息
    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO) {

        try {
            if (null == courseVO.getId()) {
                // 新增操作
                courseService.saveCourseOrTeacher(courseVO);
                // 返回结果
                return new ResponseResult(true, 200, "新增成功", null);
            } else {
                // 修改操作
                courseService.updateCourseOrTeacher(courseVO);
                return new ResponseResult(true, 200, "修改成功", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 回显课程信息(根据id查询对应的课程信息及关联的讲师信息)
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(Integer id) {
        CourseVO courseVO = courseService.findCourseById(id);
        return new ResponseResult(true, 200, "响应成功", courseVO);
    }

    // 修改课程状态
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(Integer id, Integer status) {

        courseService.updateCourseStatus(id, status);

        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        return new ResponseResult(true, 200, "课程状态修改成功", map);
    }

}
