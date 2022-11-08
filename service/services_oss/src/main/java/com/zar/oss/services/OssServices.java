package com.zar.oss.services;

import org.springframework.web.multipart.MultipartFile;

public interface OssServices {
    /**
     * 上传头像到oss
     * @param file
     * @return
     */
    String uploadAvatarFile(MultipartFile file) throws Exception;
}
