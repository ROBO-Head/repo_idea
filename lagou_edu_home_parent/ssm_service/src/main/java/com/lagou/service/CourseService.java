package com.lagou.service;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CourseService {

    // 多条件课程列表查询
    List<Course> findCourseByCondition(CourseVO courseVO);

    // 添加课程和讲师信息
    void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;

    // 回显课程信息(根据id查询对应的课程信息及关联的讲师信息)
    CourseVO findCourseById(Integer id);

    // 更新课程和讲师信息
    void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException;

    // 课程状态管理
    void updateCourseStatus(Integer id, Integer status);
}
