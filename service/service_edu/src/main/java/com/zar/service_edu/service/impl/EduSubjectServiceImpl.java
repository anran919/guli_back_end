package com.zar.service_edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zar.service_base.handler.exception.MyException;
import com.zar.service_edu.entity.EduSubject;
import com.zar.service_edu.entity.EduTeacher;
import com.zar.service_edu.entity.excel.SubjectData;
import com.zar.service_edu.entity.tree_subject.Level1;
import com.zar.service_edu.entity.tree_subject.Level2;
import com.zar.service_edu.mapper.EduSubjectMapper;
import com.zar.service_edu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zar
 * @since 2022-11-09
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    private EduSubjectService service = this;

    @Override
    public void uploadFile(@ApiParam(name = "file", value = "文件", required = true) @RequestParam("file") MultipartFile file) {
        InputStream in = null;
        try {
            in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new MyAnalysisEventListener()).sheet().doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    @Override
    public List<Level1> nestedList() {
//       获取一级分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(EduSubject::getParentId, 0)
                .orderByAsc(EduSubject::getSort, EduSubject::getId);
        List<EduSubject> level1List = service.list(wrapper);
//       获取二级分类
        wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .ne(EduSubject::getParentId, 0)
                .orderByAsc(EduSubject::getSort, EduSubject::getId);
        List<EduSubject> level2List = service.list(wrapper);
        List<Level1> tree = new ArrayList<>();
        for (int i = 0; i < level1List.size(); i++) {
            EduSubject level1 = level1List.get(i);
            Level1 lv1 = new Level1();
            lv1.setId(level1.getId().toString());
            lv1.setTitle(level1.getTitle());
            for (int j = 0; j < level2List.size(); j++) {
                EduSubject level2  = level2List.get(j);
                Level2 lv2 = new Level2();
                lv2.setTitle(level2.getTitle());
                lv2.setId(level2.getId().toString());
                if (level2.getParentId().equals(level1.getId())){
                    lv1.getChildren().add(lv2);
                }
            }
            tree.add(lv1);
        }
        return tree;
    }


    private class MyAnalysisEventListener extends AnalysisEventListener<SubjectData> {

        @Override
        public void invokeHeadMap(Map headMap, AnalysisContext context) {
            super.invokeHeadMap(headMap, context);
            System.out.println("--读取表头--" + headMap);
        }

        @Override
        public void invoke(SubjectData data, AnalysisContext analysisContext) {
            System.out.println("---" + data);
            if (data == null) {
                throw new MyException(20001, "文件数据为空!");
            }
//            1级分类不能重复添加
            EduSubject level1 = this.existLevel1(data.getLevel1());
            if (level1 == null) {
                level1 = new EduSubject();
                level1.setParentId(0L);
                level1.setTitle(data.getLevel1());
                boolean b = service.save(level1);
            }
//            2级分类不能重复添加
            EduSubject level2 = this.existLevel2(data.getLevel2(), level1.getId());
            if (level2 == null) {
                level2 = new EduSubject();
                level2.setParentId(level1.getId());
                level2.setTitle(data.getLevel2());
                boolean b = service.save(level2);
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        }

        /**
         * 1级分类是否存在
         *
         * @param name
         * @return
         */
        private EduSubject existLevel1(String name) {
            QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(EduSubject::getTitle, name).eq(EduSubject::getParentId, "0");
            EduSubject obj = service.getOne(wrapper);
            return obj;
        }

        /**
         * 2级分类是否存在
         *
         * @param name
         * @return
         */
        private EduSubject existLevel2(String name, Long pid) {
            QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(EduSubject::getTitle, name).eq(EduSubject::getParentId, pid);
            EduSubject obj = service.getOne(wrapper);
            return obj;
        }
    }
}
