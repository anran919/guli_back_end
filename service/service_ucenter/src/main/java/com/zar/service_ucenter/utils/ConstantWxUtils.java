package com.zar.service_ucenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantWxUtils implements InitializingBean {
    @Value("${wx.open.appid}")
    private String appid;
    @Value("${wx.open.appsecret}")
    private String appsecret;
    @Value("${wx.open.redirecturl}")
    private String redirectUrl;

    public static  String WX_OPEN_APPID;
    public static  String WX_OPEN_APP_SECRET;
    public static  String WX_OPEN_REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APPID = appid;
        WX_OPEN_APP_SECRET = appsecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }
}
