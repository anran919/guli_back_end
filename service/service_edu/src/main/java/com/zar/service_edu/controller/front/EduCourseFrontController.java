package com.zar.service_edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zar.service_edu.entity.EduCourse;
import com.zar.service_edu.entity.EduTeacher;
import com.zar.service_edu.entity.vo.ChapterVo;
import com.zar.service_edu.entity.vo.CourseInfoVo;
import com.zar.service_edu.entity.vo.front.FrontCourseVo;
import com.zar.service_edu.service.EduChapterService;
import com.zar.service_edu.service.EduCourseService;
import com.zar.service_edu.service.EduTeacherService;
import com.zar.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "客户端课程接口")
@RestController()
@CrossOrigin
@RequestMapping("/service_edu/front/course")
public class EduCourseFrontController {
    @Resource
    private EduCourseService courseService;
    @Resource
    private EduChapterService chapterService;

    @ApiOperation("条件查询课程分页列表")
    @GetMapping("pageList/{pageNo}/{pageSize}")
    public R getTeacherListByCondition(@PathVariable Long pageNo, @PathVariable Long pageSize, FrontCourseVo vo){
        Page<EduCourse> page = new Page<>(pageNo,pageSize);
        Page<EduCourse> coursePage = courseService.getCourseList(page, vo);
        return R.ok().data("data",coursePage);
    }

    @ApiOperation("条件查询课程分页列表")
    @GetMapping("{courseId}")
    public R getCourseById(@PathVariable String courseId){
        //        章节信息,小节信息
        List<ChapterVo> chapters = chapterService.getChapterVideoByCourseId(courseId);
        FrontCourseVo cvo = courseService.getFrontCourseById(courseId);
        return R.ok().data("chapters",chapters).data("cvo",cvo);
    }
}
