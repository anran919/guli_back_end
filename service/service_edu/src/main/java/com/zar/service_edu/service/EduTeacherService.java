package com.zar.service_edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zar.service_edu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zar
 * @since 2022-11-06
 */
public interface EduTeacherService extends IService<EduTeacher> {

    HashMap<String, Object> getTeacherList(Page<EduTeacher> page);
}
