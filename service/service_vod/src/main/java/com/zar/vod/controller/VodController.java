package com.zar.vod.controller;


import com.zar.commonUtils.R;
import com.zar.vod.service.VodService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@CrossOrigin
@RequestMapping("service_vod/video")
@RestController
@Api(description = "视频点播")
public class VodController {
    @Resource
    private VodService service;
    @PostMapping("upload")
    public R upload(MultipartFile file){
       R r =   service.uploadFile(file);
       return r;
    }

    @DeleteMapping("{videoId}")
    public R delete(@PathVariable  String videoId){
        R r =   service.deleteFile(videoId);
        return r;
    }
}
