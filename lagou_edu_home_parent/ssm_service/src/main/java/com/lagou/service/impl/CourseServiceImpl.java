package com.lagou.service.impl;

import com.lagou.dao.CourseMapper;
import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.Teacher;
import com.lagou.service.CourseService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findCourseByCondition(CourseVO courseVO) {
        return courseMapper.findCourseByCondition(courseVO);
    }

    @Override
    public void saveCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {
        // 封装课程信息
        Course course = new Course();
        BeanUtils.copyProperties(course, courseVO);

        // 补全课程信息: 创建&修改时间
        Date date = new Date();
        course.setCreateTime(date);
        course.setUpdateTime(date);

        // 调用dao层保存课程信息
        courseMapper.saveCourse(course);
        int id = course.getId();

        // 封装讲师信息
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher, courseVO);

        // 补全讲师信息: 创建&修改时间, courseId
        teacher.setCreateTime(date);
        teacher.setUpdateTime(date);
        teacher.setCourseId(id);

        // 调用dao层保存讲师信息
        courseMapper.saveTeacher(teacher);

    }

    @Override
    public CourseVO findCourseById(Integer id) {
        return courseMapper.findCourseById(id);
    }

    @Override
    public void updateCourseOrTeacher(CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {
        // 封装课程信息
        Course course = new Course();
        BeanUtils.copyProperties(course, courseVO);

        // 补全课程信息
        Date date = new Date();
        course.setUpdateTime(date);

        // 调用dao
        courseMapper.updateCourse(course);

        // 封装讲师信息
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher, courseVO);

        // 补全讲师信息
        teacher.setCourseId(course.getId());
        teacher.setUpdateTime(date);

        // 调用dao
        courseMapper.updateTeacher(teacher);

    }

    @Override
    public void updateCourseStatus(Integer id, Integer status) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(status);
        course.setUpdateTime(new Date());

        courseMapper.updateCourseStatus(course);
    }
}
