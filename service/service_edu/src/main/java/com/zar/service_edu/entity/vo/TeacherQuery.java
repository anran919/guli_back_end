package com.zar.service_edu.entity.vo;

import com.zar.service_edu.entity.EduTeacher;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "teacher查询对象",description = "讲师查询对象封装")
@Data
public class TeacherQuery extends EduTeacher implements Serializable {
    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String gmtCreateBegin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换
    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String gmtCreateEnd;
    @ApiModelProperty(value = "分页", example = "1")
    private int pageNo;
    @ApiModelProperty(value = "分页", example = "10")
    private int pageSize;
}