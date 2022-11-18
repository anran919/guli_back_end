package com.zar.service_edu.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zar.utils.R;
import com.zar.service_edu.entity.EduCourse;
import com.zar.service_edu.entity.EduTeacher;
import com.zar.service_edu.service.EduCourseService;
import com.zar.service_edu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(description = "客户端首页接口")
@RestController()
@CrossOrigin
@RequestMapping("/service_edu/front")
public class IndexFrontController {

    @Resource
    private EduTeacherService teacherService;
    @Resource
    private EduCourseService courseService;

    @GetMapping("index")
    @ApiOperation(value = "获取客户端首页信息")
    public R index(){
//        获取名师
        QueryWrapper<EduTeacher> tw = new QueryWrapper<>();
        tw.orderByDesc("id");
        tw.last("limit 4");
        List<EduTeacher> teachers = teacherService.list(tw);
//        获取热门课程
        QueryWrapper<EduCourse> cw = new QueryWrapper<>();
        tw.orderByDesc("id");
        tw.last("limit 8");
        List<EduCourse> courses = courseService.list(cw);

        return R.ok().data("teachers",teachers).data("courses",courses);
    }
}
