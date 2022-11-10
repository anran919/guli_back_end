package com.zar.service_edu.controller;


import com.zar.commonUtils.R;
import com.zar.service_edu.entity.EduCourse;
import com.zar.service_edu.entity.vo.CourseInfoVo;
import com.zar.service_edu.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "上传课程封面")
    @PostMapping("uploadCover")
    public R upLoadFile(MultipartFile file) {
        courseService.uploadCover(file);
        return R.ok();
    }

}

