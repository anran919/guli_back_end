package com.zar.service_edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zar.commonUtils.R;
import com.zar.service_base.handler.exception.MyException;
import com.zar.service_edu.entity.EduTeacher;
import com.zar.service_edu.entity.vo.TeacherQuery;
import com.zar.service_edu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zar
 * @since 2022-11-06
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/service_edu/edu-teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询所有教师数据
     *
     * @return 教师数据
     */
    @ApiOperation(value = "查询所有教师数据")
    @GetMapping("getTeacherList")
    public R getTeacherList() {
        try {
            int a = 10/0; }catch(Exception e) {
            throw new MyException(20001,"出现自定义异常");
        }
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("getUserPageList/{page}/{limit}")
    public R getUserPageList(@ApiParam(required = true) @PathVariable Long page, @ApiParam(required = true) @PathVariable Long limit) {
        Page<EduTeacher> iPage = new Page<>(page, limit);
        IPage<EduTeacher> eduTeacherIPage = teacherService.page(iPage, null);
        long total = iPage.getTotal();
        List<EduTeacher> records = eduTeacherIPage.getRecords();
        return R.ok().data("total", total).data("records", records);
    }

    /**
     * 多条件组合查询get
     *
     * @param page
     * @param limit
     * @param query
     * @return
     */
    @GetMapping("getTeacherPageListCondition/{page}/{limit}")
    public R getTeacherPageListCondition(@PathVariable long page, @PathVariable long limit, TeacherQuery query) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
//        多条件组合查询
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = query.getName();
        Integer level = query.getLevel();
        String begin = query.getBegin();
        String end = query.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        IPage<EduTeacher> iPage = teacherService.page(teacherPage, wrapper);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = iPage.getRecords();
        return R.ok().data("total", total).data("items", records);
    }


    /**
     * 多条件组合查询post
     *
     * @param query
     * @return
     */
    @PostMapping("postTeacherPageListCondition")
    public R postTeacherPageListCondition(@RequestBody(required = false) TeacherQuery query) {
        int pageNo = query.getPageNo();
        int pageSize = query.getPageSize();
        String name = query.getName();
        Integer level = query.getLevel();
        String begin = query.getBegin();
        String end = query.getEnd();
        Page<EduTeacher> page = new Page<>(pageNo, pageSize);
//        多条件组合查询
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        IPage<EduTeacher> iPage = teacherService.page(page, wrapper);
        long total = page.getTotal();
        List<EduTeacher> records = iPage.getRecords();
        return R.ok().data("total", total).data("items", records);
    }


    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("{id}")
    public R removeById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        boolean b = teacherService.removeById(id);
        return b ? R.ok() : R.error();
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("add")
    public R save(@ApiParam(name = "teacher", value = "讲师对象", required = true) @RequestBody EduTeacher teacher) {
        boolean save = teacherService.save(teacher);
        return save ? R.ok() : R.error();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("data", teacher);
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("{id}")
    public R updateById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id, @ApiParam(name = "teacher", value = "讲师对象", required = true) @RequestBody EduTeacher t) {
        t.setId(id);
        boolean b = teacherService.updateById(t);
        return b ? R.ok() : R.error();
    }


}

