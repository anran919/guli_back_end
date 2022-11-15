package com.zar.service_edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zar.utils.R;
import com.zar.service_edu.entity.EduCourse;
import com.zar.service_edu.entity.vo.CourseInfoVo;
import com.zar.service_edu.entity.vo.CoursePublishVo;
import com.zar.service_edu.service.EduCourseService;
import com.zar.service_edu.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zar
 * @since 2022-11-10
 */
@RestController
@RequestMapping("/service_edu/course")
@Api(description = "课程管理")
@CrossOrigin
public class EduCourseController {

    @Resource
    EduCourseService courseService;

    @GetMapping("list")
    @ApiOperation(value = "添加课程基本信息")
    public R list( CourseInfoVo vo){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        Integer pageSize = vo.getPageSize();
        Integer pageNo = vo.getPageNo();
        String title = vo.getTitle();
        Integer status = vo.getStatus();
        String createBegin = vo.getGmtModifiedBegin();
        String createEnd = vo.getGmtModifiedEnd();
        if (createBegin!=null&&createEnd!=null){
            wrapper.between("gmt_create",DateUtil.getDate(createBegin),DateUtil.getDate(createEnd));
        }
        wrapper.lambda().like(!StringUtils.isEmpty(title),EduCourse::getTitle,vo.getTitle())
                        .like(status!=null, EduCourse::getStatus, status);
        wrapper.orderByDesc("gmt_create");
        IPage<EduCourse> iPage = new Page<>(pageNo,pageSize);
        IPage<EduCourse> list = courseService.page(iPage, wrapper);
        return R.ok().data("data", list.getRecords()).data("total",iPage.getTotal());
    }

    @PostMapping("add")
    @ApiOperation(value = "添加课程基本信息")
    public R add(@RequestBody CourseInfoVo vo){
//        返回添加的课程信息
        EduCourse course =   courseService.addCourse(vo);
        return R.ok().data("data",course);
    }

    @ApiOperation("通过id查询课程信息")
    @GetMapping("{id}")
    public R getCourseById(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable String id){
        CourseInfoVo course = courseService.getCourseById(id);
        return R.ok().data("data",course);
    }

    @ApiOperation("修改课程信息")
    @PutMapping("update")
    public R updateCourse(@RequestBody CourseInfoVo info){
        courseService.updateCourse(info);
        return R.ok();
    }

    @ApiOperation("获取课程发布信息")
    @GetMapping("getCoursePublish/{courseId}")
    public R getCoursePublish(@PathVariable String courseId){
        CoursePublishVo vo =  courseService.getCoursePublish(courseId);
        return R.ok().data("data",vo);
    }

    @ApiOperation("删除课课程信息")
    @DeleteMapping("{courseId}")
    public R delete(@PathVariable String courseId){
        courseService.deleteByCourseId(courseId);
        return R.ok();
    }

}

