package com.zar.oss.controller;

import com.zar.commonUtils.R;
import com.zar.oss.services.OssServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Api("上传文件")
@RestController
@RequestMapping("/edu_oss/file_oss")
@CrossOrigin
public class OssController {
    @Resource
    OssServices ossServices;

    @ApiOperation(value = "上传头像文件")
    @PostMapping("upload")
    public R uploadOssFile(@ApiParam(name = "file",value = "文件" ,required = true) @RequestParam("file") MultipartFile file){
//      返回上传到oss的路径
        String url = null;
        try {
            url = ossServices.uploadAvatarFile(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return R.ok().data("url",url);
    }
}
