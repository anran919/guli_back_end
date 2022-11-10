package com.zar.service_edu.controller;


import com.zar.commonUtils.R;
import com.zar.service_edu.entity.tree_subject.Level1;
import com.zar.service_edu.service.EduSubjectService;
import com.zar.service_edu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

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
@RequestMapping("service_edu/subject")
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
    @ApiOperation(value = "获取课程树形数据")
    @GetMapping("getTreeList")
    public R getTreeList(){
        List<Level1> list =  subjectService.nestedList();
        return R.ok().data("list",list);
    }

}

