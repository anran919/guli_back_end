package com.zar.service_edu.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zar.service_edu.entity.EduCourse;
import com.zar.service_edu.entity.EduTeacher;
import com.zar.service_edu.service.EduCourseService;
import com.zar.service_edu.service.EduTeacherService;
import com.zar.utils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Api(description = "客户端名师接口")
@RestController()
@CrossOrigin
@RequestMapping("/service_edu/front/teacher")
public class EduTeacherFrontController {

    @Resource
    private EduTeacherService teacherService;
    @Resource
    private EduCourseService courseService;

    @GetMapping("pageList/{pageNo}/{pageSize}")
    public R getTeacherList(@PathVariable Long pageNo, @PathVariable Long pageSize){
        Page<EduTeacher> page = new Page<>(pageNo,pageSize);
        HashMap<String,Object> map = teacherService.getTeacherList(page);
        return R.ok().data(map);
    }

    @GetMapping("info/{id}")
    public R getTeacherInfoById(@PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
}
