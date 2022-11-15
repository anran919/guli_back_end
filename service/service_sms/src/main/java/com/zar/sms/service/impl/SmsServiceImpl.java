package com.zar.sms.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.zar.sms.service.SmsService;
import com.zar.sms.utils.ConstantUtil;
import com.zar.utils.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public boolean sendSmsCode(String phone,String code) {
        String accessKeyId = ConstantUtil.ACCESS_KEY_ID;
        String secret = ConstantUtil.ACCESS_KEY_SECRET;
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,secret );
        IAcsClient client = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setSignName("阿里云短信测试");
//        SMS_154950909 测试模板,阿里云提供测试模板
        request.setTemplateCode("SMS_154950909");
        request.setPhoneNumbers(phone);
        //      生成随机值,由阿里云发送

        HashMap<String, String> params = new HashMap<>();
        params.put("code",code);
        request.setTemplateParam(new Gson().toJson(params));
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
            if (response.getCode().equals("OK")){
                return true;
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return  false;
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
            return  false;
        }

        return false;
    }
}
