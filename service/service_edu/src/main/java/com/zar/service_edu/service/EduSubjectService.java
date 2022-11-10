package com.zar.service_edu.service;

import com.zar.service_edu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zar.service_edu.entity.tree_subject.Level1;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zar
 * @since 2022-11-09
 */
public interface EduSubjectService extends IService<EduSubject> {

    void uploadFile(MultipartFile file);

    List<Level1> nestedList();
}
