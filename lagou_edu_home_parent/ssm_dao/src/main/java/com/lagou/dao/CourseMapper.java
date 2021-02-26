package com.lagou.dao;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.Teacher;

import java.util.List;

/**
 * 课程信息管理
 */
public interface CourseMapper {

    // 多条件课程列表查询
    List<Course> findCourseByCondition(CourseVO courseVO);

    // 新增课程信息
    void saveCourse(Course course);

    // 新增讲师信息
    void saveTeacher(Teacher teacher);

    // 回显课程信息(根据id查询对应的课程信息及关联的讲师信息)
    CourseVO findCourseById(Integer id);

    // 更新课程信息
    void updateCourse(Course course);

    // 更新讲师信息
    void updateTeacher(Teacher teacher);

    // 课程状态管理
    void updateCourseStatus(Course course);

}
