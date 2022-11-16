package com.zar.service_ucenter.controller;


import com.zar.service_base.handler.exception.MyException;
import com.zar.service_ucenter.entity.UcenterMember;
import com.zar.service_ucenter.entity.vo.AuthVo;
import com.zar.service_ucenter.entity.vo.LoginVo;
import com.zar.service_ucenter.entity.vo.RegisterVo;
import com.zar.service_ucenter.service.UcenterMemberService;
import com.zar.utils.JwtUtils;
import com.zar.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zar
 * @since 2022-11-16
 */
@Api(tags = "会员管理")
@RestController
@RequestMapping("/service_ucenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Resource
    UcenterMemberService memberService;

    @ApiOperation("会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo vo) {
        String token = memberService.login(vo);
        return R.ok().data("token", token);
    }


    @ApiOperation("会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo vo) {
        boolean b = memberService.register(vo);
        if (b) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation("根据token获取登录信息")
    @GetMapping("auth")
    public R auth(HttpServletRequest request) {
        try {
            String id = JwtUtils.getMemberIdByJwtToken(request);
            AuthVo auth = memberService.getAuth(id);
            return R.ok().data("data", auth);
        } catch (Exception e) {
            throw new MyException(20001,e.toString());
        }

    }

}

