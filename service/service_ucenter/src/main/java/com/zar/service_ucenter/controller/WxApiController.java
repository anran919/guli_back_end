package com.zar.service_ucenter.controller;

import com.zar.service_base.handler.exception.MyException;
import com.zar.service_ucenter.utils.ConstantWxUtils;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


@Api(tags = "微信登录")
@Controller
@RequestMapping("/service_ucenter/api/wx")
@CrossOrigin
public class WxApiController {
    @GetMapping("login")
    public String getWxQrConnect() {
        //    生成微信扫描二维码
        //   微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        // 回调地址
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new MyException(20001,e.getMessage());
        }
        String state = "atgugu";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APPID,
                redirectUrl,
                state);
        return "redirect:" + qrcodeUrl;
    }

}
