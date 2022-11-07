package com.zar.service_edu.controller;

import com.zar.commonUtils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Api(description = "登录模块" )
@CrossOrigin
@RequestMapping("/edu_service/user")
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
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
