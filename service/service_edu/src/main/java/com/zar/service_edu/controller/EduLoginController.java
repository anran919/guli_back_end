package com.zar.service_edu.controller;

import com.zar.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Api(description = "登录模块" )
@CrossOrigin
@RequestMapping("/service_edu/user")
public class EduLoginController {

    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }
    @ApiOperation(value = "获取用户信息")
    @GetMapping("info")
    public R info(){
        ArrayList<String> roles = new ArrayList<>();
        roles.add("admin");
        return R.ok()
                .data("roles",roles)
                .data("name","管理员")
                .data("introduction","我是一个超级管理员")
                .data("avatar","https://zar-edu.oss-cn-hangzhou.aliyuncs.com/edu_upload/2022/11/09/2ad92547-b965-4086-a575-7cc6410590ad.png");
    }
}
