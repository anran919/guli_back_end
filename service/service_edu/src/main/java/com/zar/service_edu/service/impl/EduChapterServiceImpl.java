package com.zar.service_edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zar.service_edu.entity.EduChapter;
import com.zar.service_edu.entity.EduVideo;
import com.zar.service_edu.entity.vo.ChapterVo;
import com.zar.service_edu.entity.vo.VideoVo;
import com.zar.service_edu.mapper.EduChapterMapper;
import com.zar.service_edu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zar.service_edu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zar
 * @since 2022-11-10
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    private EduVideoService videoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
//       通过课程id获取章节列表
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        chapterWrapper.orderByAsc("sort");
        List<EduChapter> chapters = this.list(chapterWrapper);
//      通过课程id获取小节列表
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        chapterWrapper.orderByAsc("sort");
        List<EduVideo> videos = videoService.list(videoWrapper);
//      返回结果封装
        List<ChapterVo> cvos = new ArrayList<>();
        chapters.forEach(c->{    // 遍历章节
            ChapterVo cvo = new ChapterVo();
            BeanUtils.copyProperties(c,cvo);
            ArrayList<VideoVo> voList = new ArrayList<>();
            videos.forEach(v->{ //遍历小节
                if (v.getChapterId().equals(c.getId())){
                    VideoVo vvo = new VideoVo();
                    BeanUtils.copyProperties(v,vvo);
                    voList.add(vvo);
                }
            });
            cvo.setChildren(voList);
            cvos.add(cvo);
        });
        return cvos;
    }
}
