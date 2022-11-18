package com.zar.service_edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zar.service_edu.entity.EduTeacher;
import com.zar.service_edu.mapper.EduTeacherMapper;
import com.zar.service_edu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zar
 * @since 2022-11-06
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public HashMap<String, Object> getTeacherList(Page<EduTeacher> page) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        this.page(page, wrapper);
        long total = page.getTotal();
        long pages = page.getPages();
        long current = page.getCurrent();
        List<EduTeacher> records = page.getRecords();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("pages",pages);
        map.put("current",current);
        map.put("records",records);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }
}
