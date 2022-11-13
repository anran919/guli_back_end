package com.zar.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

public class AliyunVodSDKUtils {
    public static DefaultAcsClient initVodClient() throws ClientException {
        String accessKeyId = ConstantVodUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantVodUtil.ACCESS_KEY_SECRET;
        String regionId = "cn-shanghai"; // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId,
                accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client; }
}
