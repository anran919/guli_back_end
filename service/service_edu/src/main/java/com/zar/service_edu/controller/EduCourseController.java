package com.zar.service_edu.controller;


import com.zar.commonUtils.R;
import com.zar.service_edu.entity.EduCourse;
import com.zar.service_edu.entity.EduTeacher;
import com.zar.service_edu.entity.vo.CourseInfoVo;
import com.zar.service_edu.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


}

