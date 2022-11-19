package com.zar.service_edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zar.service_base.handler.exception.MyException;
import com.zar.service_edu.entity.EduCourse;
import com.zar.service_edu.entity.EduCourseDescription;
import com.zar.service_edu.entity.vo.CourseInfoVo;
import com.zar.service_edu.entity.vo.CoursePublishVo;
import com.zar.service_edu.entity.vo.front.FrontCourseVo;
import com.zar.service_edu.mapper.EduCourseMapper;
import com.zar.service_edu.service.EduChapterService;
import com.zar.service_edu.service.EduCourseDescriptionService;
import com.zar.service_edu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zar.service_edu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    @Resource
    private EduCourseMapper courseMapper;

    @Resource
    private EduChapterService chapterService;

    @Resource
    private EduVideoService videoService;

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
        if (course!=null)  BeanUtils.copyProperties(course, vo);
        if (des!=null)  BeanUtils.copyProperties(des, vo);
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

    @Override
    public CoursePublishVo getCoursePublish(String courseId) {
        CoursePublishVo info = courseMapper.getCoursePublishInfo(courseId);
        return info;
    }

    @Override
    public void deleteByCourseId(String courseId) {
        //       删除小节
        videoService.deleteByCourseId(courseId);
        //       删除章节
        chapterService.deleteByCourseId(courseId);
        //       删除描述  描述id和 课程id存储的是一个id ,直接可以删除
        descriptionService.removeById(courseId);
        //
        boolean b = this.removeById(courseId);
        if (!b){
            throw new MyException(2001,"删除失败");
        }
    }

    @Override
    public Page<EduCourse> getCourseList(Page<EduCourse> page, FrontCourseVo vo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(!StringUtils.isEmpty(vo.getTeacherId()),EduCourse::getTeacherId,vo.getTeacherId())
                .eq(!StringUtils.isEmpty(vo.getSubjectParentId()),EduCourse::getSubjectParentId,vo.getSubjectParentId())
                .eq(!StringUtils.isEmpty(vo.getSubjectId()),EduCourse::getSubjectId,vo.getSubjectId())
                .orderByDesc(!StringUtils.isEmpty(vo.getPrice()),EduCourse::getPrice)
                .orderByDesc(!StringUtils.isEmpty(vo.getBuyCount()),EduCourse::getBuyCount)
                .orderByDesc(!StringUtils.isEmpty(vo.getGmtCreate()),EduCourse::getGmtCreate);
        this.page(page, wrapper);
        return page;
    }

    @Override
    public FrontCourseVo getFrontCourseById(String courseId) {
//        课程简介教师信息
        FrontCourseVo cvo = baseMapper.getFrontCourseById(courseId);
        return cvo;
    }

}
