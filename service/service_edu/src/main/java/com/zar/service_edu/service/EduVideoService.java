package com.zar.service_edu.service;

import com.zar.service_edu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author zar
 * @since 2022-11-10
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteByCourseId(String courseId);
}
