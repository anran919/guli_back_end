package com.zar.service_edu.controller;


import com.zar.commonUtils.R;
import com.zar.service_edu.service.EduSubjectService;
import com.zar.service_edu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zar
 * @since 2022-11-09
 */

@RestController
@Api(description ="课程科目" )
@RequestMapping("service_edu/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Resource
    EduSubjectService subjectService;

    @ApiOperation(value = "上传课程文件")
    @PostMapping("uploadFile")
    public R upLoadFile(MultipartFile file) {
        subjectService.uploadFile(file);
        return R.ok();
    }

}

