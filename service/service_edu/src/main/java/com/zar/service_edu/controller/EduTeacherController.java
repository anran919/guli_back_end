package com.zar.service_edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zar.commonUtils.R;
import com.zar.service_edu.entity.EduTeacher;
import com.zar.service_edu.entity.vo.TeacherQuery;
import com.zar.service_edu.service.EduTeacherService;
import com.zar.service_edu.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;


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
@RequestMapping("/edu_service/edu_teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 查询所有教师数据
     *
     * @return 教师数据
     */
    @ApiOperation(value = "查询所有教师数据")
    @GetMapping("getTeacherList")
    public R getTeacherList() {
//        try {
//            int a = 10/0; }catch(Exception e) {
//            throw new MyException(20001,"出现自定义异常!");
//        }
        Page<EduTeacher> iPage = new Page<>(0, 10);
        IPage<EduTeacher> sPage = teacherService.page(iPage, null);
        List<EduTeacher> records = sPage.getRecords();
        return R.ok().data("items", records).data("total", iPage.getTotal()).data("pageSize", iPage.getSize()).data("pageNo", iPage.getCurrent());
    }

    @ApiOperation(value = "分页条件查询所有教师数据")
    @GetMapping("getList/{pageNo}/{pageSize}")
    public R getList(@PathVariable Integer pageNo, @PathVariable Integer pageSize, TeacherQuery teacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacher.getName();
        Integer level = teacher.getLevel();
        String gmtCreateBegin =teacher.getGmtCreateBegin();
        String gmtCreateEnd = teacher.getGmtCreateEnd();
        if (!isBlank(name)) {
            wrapper.like("name", name);
        }
        if (level != null) {
            wrapper.like("level", level);
        }
        if (gmtCreateBegin != null && gmtCreateEnd != null) {
            Date begin =  DateUtil.getDate(gmtCreateBegin);
            Date end =  DateUtil.getDate(gmtCreateEnd);
            wrapper.between("gmt_create", begin, end);
        }
        IPage<EduTeacher> iPage = new Page<>(pageNo, pageSize);
        teacherService.page(iPage, wrapper);
        return R.ok().data("items", iPage.getRecords()).data("total", iPage.getTotal());
    }

    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("getPageList/{page}/{limit}")
    public R getPageList(@ApiParam(required = true) @PathVariable Long page, @ApiParam(required = true) @PathVariable Long limit) {
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
    @GetMapping("getPageListCondition/{page}/{limit}")
    public R getTeacherPageListCondition(@PathVariable long page, @PathVariable long limit, TeacherQuery query) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
//        多条件组合查询
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = query.getName();
        Integer level = query.getLevel();
        String begin = query.getGmtCreateBegin();
        String end = query.getGmtCreateEnd();
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
    @PostMapping("postPageListCondition")
    public R postTeacherPageListCondition(@RequestBody(required = false) TeacherQuery query) {
        int pageNo = query.getPageNo();
        int pageSize = query.getPageSize();
        String name = query.getName();
        Integer level = query.getLevel();
        String begin = query.getGmtCreateBegin();
        String end = query.getGmtCreateEnd();
        Page<EduTeacher> page = new Page<>(pageNo, pageSize);
//        多条件组合查询
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
//        %cc
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

