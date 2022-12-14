package com.zar.service_edu.service;

import com.zar.service_edu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zar.service_edu.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zar
 * @since 2022-11-10
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    void deleteByCourseId(String courseId);
}
