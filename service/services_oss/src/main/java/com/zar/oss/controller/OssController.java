package com.zar.oss.controller;

import com.zar.commonUtils.R;
import com.zar.oss.services.OssServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @PostMapping
    public R uploadOssFile(MultipartFile file){
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
