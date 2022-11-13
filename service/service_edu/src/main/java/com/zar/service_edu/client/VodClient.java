package com.zar.service_edu.client;

import com.zar.commonUtils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-vod")
public interface VodClient {
    @DeleteMapping("service_vod/video/{videoId}")
//    PathVariable 一定要写参数名称
    public R delete(@PathVariable("videoId") String videoId);
}
