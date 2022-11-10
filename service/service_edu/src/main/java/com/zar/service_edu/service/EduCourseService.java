package com.zar.service_edu.service;

import com.zar.service_edu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zar.service_edu.entity.vo.CourseInfoVo;
import org.springframework.web.multipart.MultipartFile;

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

}
