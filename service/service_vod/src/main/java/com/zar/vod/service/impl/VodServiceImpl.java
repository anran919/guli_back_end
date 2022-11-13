package com.zar.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.zar.commonUtils.R;
import com.zar.service_base.handler.exception.MyException;
import com.zar.vod.service.VodService;
import com.zar.vod.utils.AliyunVodSDKUtils;
import com.zar.vod.utils.ConstantVodUtil;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {


    @Override
    public R uploadFile(MultipartFile file) {
        String accessKeyId = ConstantVodUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantVodUtil.ACCESS_KEY_SECRET;
        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0, fileName.lastIndexOf("."));
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        Response res = new Response();
        if (response.isSuccess()){
            res.code = "200";
            res.message = "上传成功";
        }else {
            res.code = response.getCode();
            res.message = response.getMessage();
        }
        res.requestId = response.getRequestId();
        res.videoId = response.getVideoId();
        return R.ok().data("data", res);
    }

    @Override
    public R deleteFile(String videoId) {
        try {
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient();
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            DeleteVideoResponse response = client.getAcsResponse(request);
            return R.ok().data("data",response.getRequestId());
        } catch (ClientException e) {
            throw new MyException(2001,"删除失败");
        }
    }

    @Data
    class Response {
        private String videoId;
        private String requestId;
        private String code;
        private String message;
    }
}
