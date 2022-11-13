package com.zar.vod.service;

import com.zar.commonUtils.R;
import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    R uploadFile(MultipartFile file);

    R deleteFile(String videoId);
}
