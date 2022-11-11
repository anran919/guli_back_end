package com.zar.service_edu.service.impl;

import com.zar.service_base.handler.exception.MyException;
import com.zar.service_edu.entity.EduCourse;
import com.zar.service_edu.entity.EduCourseDescription;
import com.zar.service_edu.entity.vo.CourseInfoVo;
import com.zar.service_edu.mapper.EduCourseMapper;
import com.zar.service_edu.service.EduCourseDescriptionService;
import com.zar.service_edu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zar
 * @since 2022-11-10
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Resource
    private EduCourseDescriptionService descriptionService;

    @Override
    public EduCourse addCourse(CourseInfoVo vo) {
        boolean isAddSuccess;
        EduCourse bean = new EduCourse();
//      将vo转换成entity
        BeanUtils.copyProperties(vo, bean);
//        向课程表添加课程信息
        isAddSuccess = this.save(bean);
        if (!isAddSuccess) {
            throw new MyException(2001, "添加课程信息失败!");
        }
//        获取添加课程之后的课程id
        String cid = bean.getId();
//        向课程描述表添加描述信息
        EduCourseDescription description = new EduCourseDescription();
//        添加课程id
        BeanUtils.copyProperties(vo, description);
        description.setId(cid);
        isAddSuccess = descriptionService.save(description);
        if (!isAddSuccess) {
            throw new MyException(2001, "添加课程描述失败!");
        }
        return bean;
    }

    @Override
    public CourseInfoVo getCourseById(String courseId) {
//       查询课程基本信息
        EduCourse course = this.getById(courseId);
//        查询描述信息
        EduCourseDescription des = descriptionService.getById(courseId);
        CourseInfoVo vo = new CourseInfoVo();
        BeanUtils.copyProperties(course, vo);
        BeanUtils.copyProperties(des, vo);
        return vo;
    }

    @Override
    public void updateCourse(CourseInfoVo info) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(info, course);
        boolean bc = this.updateById(course);
        if (!bc) {
            throw new MyException(2001, "修改课程信息出错!");
        }
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(info, description);
        boolean bd = descriptionService.updateById(description);
        if (!bd) {
            throw new MyException(2001, "修改课程描述信息出错!");
        }
    }
}
