package com.zar.service_edu.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BaseVo {
    @ApiModelProperty(value = "pageNo")
    private Integer pageNo;
    @ApiModelProperty(value = "pageSize")
    private Integer pageSize;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;

    @ApiModelProperty(value = "更新时间/开始")
    private String gmtModifiedBegin;
    @ApiModelProperty(value = "更新时间/结束")
    private String gmtModifiedEnd;
}
