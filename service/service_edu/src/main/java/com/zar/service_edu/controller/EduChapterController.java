package com.zar.service_edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zar.utils.R;
import com.zar.service_base.handler.exception.MyException;
import com.zar.service_edu.entity.EduChapter;
import com.zar.service_edu.entity.EduVideo;
import com.zar.service_edu.entity.vo.ChapterVo;
import com.zar.service_edu.service.EduChapterService;
import com.zar.service_edu.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zar
 * @since 2022-11-10
 */

@Api(description = "章节管理")
@RestController
@CrossOrigin
@RequestMapping("/service_edu/chapter")
public class EduChapterController {
    @Resource
    private EduChapterService chapterService;
    @Resource
    private EduVideoService  videoService;

    @ApiOperation("通过课程id查询章节列表")
    @GetMapping("nested_list/{courseId}")
    public R getChapterNestedListByCourseId(@ApiParam(name = "courseId",value = "课程id", required = true) @PathVariable String courseId){
        List<ChapterVo> chapters =  chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("data",chapters);
    }

    @ApiOperation("添加章节信息")
    @PostMapping("add")
    public R addChapter( @ApiParam(name = "chapter",value = "章节信息",required = true) @RequestBody EduChapter chapter){
        boolean save = chapterService.save(chapter);
        return R.ok().data("data",save);
    }

    @ApiOperation("修改章节信息")
    @PutMapping("update")
    public R updateChapter( @ApiParam(name = "chapter",value = "章节信息",required = true) @RequestBody EduChapter chapter){
        boolean update = chapterService.updateById(chapter);
        return R.ok().data("data",update);
    }


    @ApiOperation("通过id查询章节信息")
    @GetMapping("{id}")
    public R getChapterById( @ApiParam(name = "id",value = "章节id",required = true) @PathVariable String id){
        EduChapter chapter = chapterService.getById(id);
        return R.ok().data("data",chapter);
    }

    @ApiOperation("通过id删除章节信息")
    @DeleteMapping("{id}")
    public R deleteChapterById( @ApiParam(name = "id",value = "章节id",required = true) @PathVariable String id){
//        不直接删除,判断下面是否有小节,没有再删除
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("chapter_id",id);
        int count = videoService.count(videoWrapper);
        boolean remove =false;
        if (count<1){
            remove  = chapterService.removeById(id);
        }else {
            throw new MyException(2002,"删除失败,请先删除小节!");
        }
        return R.ok().data("data",remove);
    }










}

