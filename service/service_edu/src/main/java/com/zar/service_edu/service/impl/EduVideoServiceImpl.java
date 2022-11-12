package com.zar.service_edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zar.service_edu.entity.EduVideo;
import com.zar.service_edu.mapper.EduVideoMapper;
import com.zar.service_edu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zar
 * @since 2022-11-10
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public void deleteByCourseId(String courseId) {
//        TODO 还需要删除视频文件,存储在阿里云
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        this.remove(wrapper);
    }
}
