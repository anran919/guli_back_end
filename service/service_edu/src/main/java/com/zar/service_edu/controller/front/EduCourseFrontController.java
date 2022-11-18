package com.zar.service_edu.controller.front;

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

@Api(description = "客户端课程接口")
@RestController()
@CrossOrigin
@RequestMapping("/service_edu/front/course")
public class EduCourseFrontController {
    @Resource
    private EduCourseService courseService;

    @GetMapping("pageList/{pageNo}/{pageSize}")
    public R getTeacherList(@PathVariable Long pageNo, @PathVariable Long pageSize){
        Page<EduCourse> page = new Page<>(pageNo,pageSize);
        HashMap<String,Object> map = courseService.getTeacherList(page);
        return R.ok().data(map);
    }
}
