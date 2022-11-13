package com.zar.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadFileStreamRequest;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadURLStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadFileStreamResponse;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadURLStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UploadVideoTest {
    //账号AK信息请填写（必选）
    private static final String accessKeyId = AliyunVodSDKUtils.accessKeyId;
    //账号AK信息请填写（必选）
    private static final String accessKeySecret = AliyunVodSDKUtils.accessKeySecret;
    private static final String fileName = "/Users/mac/Downloads/20221112.mov";

    public static void main(String[] args) {
        // 视频文件上传
        // 视频标题（必选）
        String title = "测试标题";
        // 1.本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如：/User/sample/文件名称.mp4 （必选）
        // 2.网络流上传时，文件名称为源文件名，如文件名称.mp4（必选）。
        // 3.流式上传时，文件名称为源文件名，如文件名称.mp4（必选）。
        // 任何上传方式文件名必须包含扩展名
        // 本地文件上传
        testUploadVideo(accessKeyId, accessKeySecret, title, fileName);

        // 待上传视频的网络流地址
        String url = "http://test.aliyun.com/video/test.mp4";

        // 2.网络流上传
        // 文件扩展名，当url中不包含扩展名时，需要设置该参数
        String fileExtension = "mp4";
        testUploadURLStream(accessKeyId, accessKeySecret, title, url, fileExtension);

        // 3.文件流上传
        testUploadFileStream(accessKeyId, accessKeySecret, title, fileName);

        // 4.流式上传，如文件流和网络流
        InputStream inputStream = null;
        // 4.1 文件流
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 4.2 网络流
        try {
            inputStream = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        testUploadStream(accessKeyId, accessKeySecret, title, fileName, inputStream);
    }

    /**
     * 本地文件上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     */
    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，（注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
    /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
    注意：断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        //request.setEnableCheckpoint(false);
        /* OSS慢请求日志打印超时时间，是指每个分片上传时间超过该阈值时会打印debug日志，如果想屏蔽此日志，请调整该阈值。单位：毫秒，默认为300000毫秒*/
        //request.setSlowRequestsThreshold(300000L);
        /* 可指定每个分片慢请求时打印日志的时间阈值，默认为300s*/
        //request.setSlowRequestsThreshold(300000L);
        /* 是否显示水印（可选），指定模板组ID时，根据模板组配置确定是否显示水印*/
        //request.setIsShowWaterMark(true);
        /* 自定义消息回调设置及上传加速设置（可选）, Extend为自定义扩展设置，MessageCallback为消息回调设置，AccelerateConfig为上传加速设置（上传加速功能需要先申请开通后才能使用）*/
        //request.setUserData("{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackType\":\"http\",\"CallbackURL\":\"http://example.aliyundoc.com\"},\"AccelerateConfig\":{\"Type\":\"oss\",\"Domain\":\"****Bucket.oss-accelerate.aliyuncs.com\"}}");
        /* 视频分类ID（可选） */
        //request.setCateId(0);
        /* 视频标签，多个用逗号分隔（可选） */
        //request.setTags("标签1,标签2");
        /* 视频描述（可选）*/
        //request.setDescription("视频描述");
        /* 封面图片（可选）*/
        //request.setCoverURL("http://cover.example.com/image_01.jpg");
        /* 模板组ID（可选）*/
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e5****");
        /* 工作流ID（可选）*/
        //request.setWorkflowId("d4430d07361f0*be1339577859b0****");
        /* 存储区域（可选）*/
        //request.setStorageLocation("in-201703232118266-5sejd****.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        //request.setPrintProgress(false);
        /* 设置自定义上传进度回调（必须继承 VoDProgressListener）*/
        /*默认关闭。如果开启了这个功能，上传过程中服务端会在日志中返回上传详情。如果不需要接收此消息，需关闭此功能*/
        //request.setProgressListener(new PutObjectProgressListener());
        /* 设置您实现的生成STS信息的接口实现类*/
        // request.setVoDRefreshSTSTokenListener(new RefreshSTSTokenImpl());
        /* 设置应用ID*/
        //request.setAppId("app-100****");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     * URL网络流上传。支持断点续传，最大支持48.8TB的单个文件。
     * 该上传方式需要先将网络文件下载到本地磁盘，再进行上传，所以要保证本地磁盘有充足的空间。
     * 当您设置的URL中不包括文件扩展名时，需要单独设置fileExtension，表示文件扩展名。
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param url
     */
    private static void testUploadURLStream(String accessKeyId, String accessKeySecret, String title, String url, String fileExtension) {
        UploadURLStreamRequest request = new UploadURLStreamRequest(accessKeyId, accessKeySecret, title, url);

        /* 文件扩展名*/
        request.setFileExtension(fileExtension);
        /* 网络文件下载连接超时，单位毫秒，0-表示不限制*/
        request.setDownloadConnectTimeout(1000);
        /* 网络文件下载读取超时，单位毫秒，0-表示不限制*/
        request.setDownloadReadTimeout(0);
        /* 网络文件下载后保存的本地目录*/
        request.setLocalDownloadFilePath("/Users/download");
        /* 是否显示水印（可选），指定模板组ID时，根据模板组配置确定是否显示水印*/
        //request.setShowWaterMark(true);
        /* 自定义消息回调设置及上传加速设置（可选），Extend为自定义扩展设置，MessageCallback为消息回调设置，AccelerateConfig为上传加速设置（上传加速功能需要先申请开通后才能使用） */
        //request.setUserData("{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackType\":\"http\",\"CallbackURL\":\"http://example.aliyundoc.com\"},\"AccelerateConfig\":{\"Type\":\"oss\",\"Domain\":\"****Bucket.oss-accelerate.aliyuncs.com\"}}");
        /* 视频分类ID（可选） */
        //request.setCateId(0);
        /* 视频标签，多个用逗号分隔（可选） */
        //request.setTags("标签1,标签2");
        /* 视频描述（可选） */
        //request.setDescription("视频描述");
        /* 封面图片（可选）*/
        //request.setCoverURL("http://cover.example.com/image_01.jpg");
        /* 模板组ID（可选）*/
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56****");
        /* 工作流ID（可选）*/
        //request.setWorkflowId("d4430d07361f0*be1339577859b0****");
        /* 存储区域（可选）*/
        //request.setStorageLocation("in-201703232118266-5sejd****.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        //request.setPrintProgress(true);
        /* 设置自定义上传进度回调 （必须继承 VoDProgressListener）*/
        /*默认关闭。如果开启了这个功能，上传过程中服务端会在日志中返回上传详情。如果不需要接收此消息，需关闭此功能*/
        //request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-100****");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadURLStreamResponse response = uploader.uploadURLStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n"); //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     * 文件流上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     */
    private static void testUploadFileStream(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadFileStreamRequest request = new UploadFileStreamRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 是否使用默认水印（可选），指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        //request.setShowWaterMark(true);
        /* 自定义消息回调设置及上传加速设置（可选），Extend为自定义扩展设置，MessageCallback为消息回调设置，AccelerateConfig为上传加速设置（上传加速功能需要先申请开通后才能使用）*/
        //request.setUserData("{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackType\":\"http\",\"CallbackURL\":\"http://example.aliyundoc.com\"},\"AccelerateConfig\":{\"Type\":\"oss\",\"Domain\":\"****Bucket.oss-accelerate.aliyuncs.com\"}}");
        /* 视频分类ID（可选）*/
        //request.setCateId(0);
        /* 视频标签，多个用逗号分隔（可选） */
        //request.setTags("标签1,标签2");
        /* 视频描述（可选）*/
        //request.setDescription("视频描述");
        /* 封面图片（可选）*/
        //request.setCoverURL("http://cover.example.com/image_01.jpg");
        /* 模板组ID（可选）*/
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56****");
        /* 工作流ID（可选）*/
        //request.setWorkflowId("d4430d07361f0*be1339577859b0****");
        /* 存储区域（可选）*/
        //request.setStorageLocation("in-201703232118266-5sejd****.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        //request.setPrintProgress(true);
        /* 设置自定义上传进度回调（必须继承 VoDProgressListener）*/
        /*默认关闭。如果开启了这个功能，上传过程中服务端会在日志中返回上传详情。如果不需要接收此消息，需关闭此功能*/
        //request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-100****");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadFileStreamResponse response = uploader.uploadFileStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n"); //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     * 流式上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     * @param inputStream
     */
    private static void testUploadStream(String accessKeyId, String accessKeySecret, String title, String fileName, InputStream inputStream) {
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        /* 是否使用默认水印（可选），指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        //request.setShowWaterMark(true);
        /* 自定义消息回调设置及上传加速设置（可选）, Extend为自定义扩展设置，MessageCallback为消息回调设置，AccelerateConfig为上传加速设置（上传加速功能需要先申请开通后才能使用）*/
        //request.setUserData("{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackType\":\"http\",\"CallbackURL\":\"http://example.aliyundoc.com\"},\"AccelerateConfig\":{\"Type\":\"oss\",\"Domain\":\"****Bucket.oss-accelerate.aliyuncs.com\"}}");
        /* 视频分类ID（可选） */
        //request.setCateId(0);
        /* 视频标签，多个用逗号分隔（可选） */
        //request.setTags("标签1,标签2");
        /* 视频描述（可选）*/
        //request.setDescription("视频描述");
        /* 封面图片（可选）*/
        //request.setCoverURL("http://cover.example.com/image_01.jpg");
        /* 模板组ID（可选）*/
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56****");
        /* 工作流ID（可选）*/
        //request.setWorkflowId("d4430d07361f0*be1339577859b0****");
        /* 存储区域（可选）*/
        //request.setStorageLocation("in-201703232118266-5sejd****.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        // request.setPrintProgress(true);
        /* 设置自定义上传进度回调（必须继承 VoDProgressListener） */
        /*默认关闭。如果开启了这个功能，上传过程中服务端会在日志中返回上传详情。如果不需要接收此消息，需关闭此功能*/
        // request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-100****");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
}