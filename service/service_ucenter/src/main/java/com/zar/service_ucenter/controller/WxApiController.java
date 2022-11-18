package com.zar.service_ucenter.controller;

import com.google.gson.Gson;
import com.zar.service_base.handler.exception.MyException;
import com.zar.service_ucenter.entity.UcenterMember;
import com.zar.service_ucenter.service.UcenterMemberService;
import com.zar.service_ucenter.utils.ConstantWxUtils;
import com.zar.utils.HttpClientUtils;
import com.zar.utils.JwtUtils;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;


@Api(tags = "微信登录")
@Controller
@RequestMapping("/service_ucenter/api/wx")
@CrossOrigin
public class WxApiController {

    @Resource
    private UcenterMemberService memberService;

    /**
     * 扫码之后的回调,获取用户信息
     *
     * @param code
     * @param state
     * @return
     */
    @GetMapping("callback")
    public String callback(String code, String state) {
        //得到授权临时票据code
        System.out.println(code);
        System.out.println(state);
        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl =
                "https://api.weixin.qq.com/sns/oauth2/access_token" +
                        "?appid=%s" +
                        "&secret=%s" +
                        "&code=%s" +
                        "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APPID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code);
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=============" + result);
        } catch (Exception e) {
            throw new MyException(20001, "获取access_token失败");
        }
        //解析json字符串
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");
        //查询数据库当前用用户是否曾经使用过微信登录
        UcenterMember member = memberService.getByOpenid(openid);
        if (member == null) {
            System.out.println("新用户注册");
        //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultUserInfo==========" + resultUserInfo);
            } catch (Exception e) {
                throw new MyException(20001, "获取用户信息失败");
            }

            //            4、业务层
            //            业务接口：MemberService.java
            //            业务实现：MemberServiceImpl.java
            //解析json
            HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String) mapUserInfo.get("nickname");
            String avatar = (String) mapUserInfo.get("headimgurl");
            //向数据库中插入一条记录
            member = new UcenterMember();
            member.setNickname(nickname);
            member.setOpenid(openid);
            member.setAvatar(avatar);
            memberService.save(member);
        }
        String jwtToken  = JwtUtils.getJwtToken(member.getId(),member.getNickname());
        return "redirect:http://localhost:3000?token="+jwtToken;
    }


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
            throw new MyException(20001, e.getMessage());
        }
        String state = "atguigu";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APPID,
                redirectUrl,
                state);
        return "redirect:" + qrcodeUrl;
    }

}
