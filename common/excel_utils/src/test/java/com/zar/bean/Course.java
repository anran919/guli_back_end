package com.zar.bean;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
public class Course {
    @ExcelProperty(value = "一级课程",index = 0)
    @ColumnWidth(25)
    private String level1;
    @ColumnWidth(25)
    @ExcelProperty(value = "二级课程",index = 1)
    private String level2;
}
