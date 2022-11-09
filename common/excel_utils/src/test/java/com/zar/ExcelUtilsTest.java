package com.zar;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.zar.bean.Course;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ExcelUtilsTest {

    public static final String FILE_PTAH ="/Users/mac/Desktop/";

    /**
     * 最简单的读
     * <p>1. 创建excel对应的实体对象
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，
     * <p>3. 直接读即可
     */
    @Test
    public void simpleRead(){
        String fileName = FILE_PTAH + "课程数据.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, Course.class, new AnalysisEventListener<Course>() {

            //             读取表头
            @Override
            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                super.invokeHeadMap(headMap, context);
                System.out.println("--读取表头--"+headMap);
            }

            @Override
            public void invoke(Course course, AnalysisContext analysisContext) {
                System.out.println("---"+course);
            }

            //            读取完成
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }


        }).sheet().doRead();
    }

    /**
     * 最简单的写
     * <p>1. 创建excel对应的实体对象
     * <p>2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        String fileName = FILE_PTAH + System.currentTimeMillis()+ ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        String sheetName ="课程信息";
        EasyExcel.write(fileName, Course.class).sheet(sheetName).doWrite(data());
    }

    private List<Course> data() {
        List<Course> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            Course data = new Course();
            data.setLevel1("一级课程"+i);
            data.setLevel2("二级课程"+i);
            list.add(data);
        }
        return list;
    }
}
