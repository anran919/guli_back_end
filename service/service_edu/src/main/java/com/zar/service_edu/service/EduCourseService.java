package com.zar.service_edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zar.service_edu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zar.service_edu.entity.vo.CourseInfoVo;
import com.zar.service_edu.entity.vo.CoursePublishVo;

import java.util.HashMap;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zar
 * @since 2022-11-10
 */
public interface EduCourseService extends IService<EduCourse> {

    EduCourse addCourse(CourseInfoVo vo);

    CourseInfoVo getCourseById(String id);

    void updateCourse(CourseInfoVo info);

    CoursePublishVo getCoursePublish(String courseId);

    void deleteByCourseId(String courseId);

    HashMap<String, Object> getTeacherList(Page<EduCourse> page);
}
