package com.zar.oss.services.impl;

import com.zar.oss.services.OssServices;
import com.zar.oss.utils.ConstantPropertiesUtil;
import com.zar.service_base.handler.exception.MyException;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServicesImpl implements OssServices {

    @Override
    public String uploadAvatarFile(MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        // 填写Bucket名称
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String uploadUrl = null;
        try {
            InputStream inputStream = file.getInputStream();
            //构建日期路径：/2019/02/26/文件名
            String filePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            //文件名：uuid.扩展名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileType = original.substring(original.lastIndexOf("."));
            String fileUrl = filePath + "/" + fileName + fileType;
            // 创建PutObject请求。
            /**
             * bucketName 填写Bucket名称
             * objectName 上传到oss文件路劲和文件名称
             * bucketName 上传文件的输入流
             */
            ossClient.putObject(bucketName, fileUrl, inputStream);
            uploadUrl = "https://" + bucketName + "." + endpoint  +"/"+ fileUrl;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (Exception e) {
            throw new MyException(500,e.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return uploadUrl;
    }
}
