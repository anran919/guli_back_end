package com.zar.vod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;


public class VodAuthSDKTest {
    /*以下为调用示例*/
    public static void main(String[] argv) {
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient();
            response = AliyunVodSDKUtils.getVideoPlayAuth(client);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");

    }
}