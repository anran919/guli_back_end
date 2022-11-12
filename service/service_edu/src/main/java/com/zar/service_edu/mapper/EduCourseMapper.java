package com.zar.service_edu.mapper;

import com.zar.service_edu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zar.service_edu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zar
 * @since 2022-11-10
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

  CoursePublishVo getCoursePublishInfo(String courseId);

}
