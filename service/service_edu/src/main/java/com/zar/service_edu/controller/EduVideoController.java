package com.zar.service_edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zar.utils.R;
import com.zar.service_edu.client.VodClient;
import com.zar.service_edu.entity.EduVideo;
import com.zar.service_edu.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zar
 * @since 2022-11-10
 */
@RestController
@Api(description = "小节管理")
@CrossOrigin
@RequestMapping("/service_edu/video")
public class EduVideoController {
    @Resource
    private EduVideoService videoService;
    @Resource
    private VodClient vodClient;

    @ApiOperation("通过章节id查询小节列表")
    @GetMapping("list/{chapterId}")
    public R getVideoListByChapterId(@ApiParam(name = "chapterId",value = "章节id", required = true) @PathVariable String chapterId){
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        List<EduVideo> videos = videoService.list(wrapper);
        return R.ok().data("data",videos);
    }

    @ApiOperation("添加小节信息")
    @PostMapping("add")
    public R addVideo( @ApiParam(name = "video",value = "章节信息",required = true) @RequestBody EduVideo video){
        boolean save = videoService.save(video);
        return R.ok().data("data",save);
    }

    @ApiOperation("修改小节信息")
    @PutMapping("update")
    public R updateVideo( @ApiParam(name = "video",value = "小节信息",required = true) @RequestBody EduVideo video){
        boolean update = videoService.updateById(video);
        return R.ok().data("data",update);
    }


    @ApiOperation("通过id查询小节信息")
    @GetMapping("{id}")
    public R getVideoById( @ApiParam(name = "id",value = "小节id",required = true) @PathVariable String id){
        EduVideo video = videoService.getById(id);
        return R.ok().data("data",video);
    }

    @ApiOperation("通过id删除小节信息")
    @DeleteMapping("{id}")
    public R deleteVideoById( @ApiParam(name = "id",value = "小节id",required = true) @PathVariable String id){
//      删除小节同时删除视频
        EduVideo video = videoService.getById(id);
        String sourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(sourceId)){
            vodClient.delete(sourceId);
        }
        boolean remove = videoService.removeById(id);
        return R.ok().data("data",remove);
    }
}

