package com.zar.service_edu.entity.vo;


import lombok.Data;

@Data
public class CoursePublishVo {
    private String id;
    public String title;
    public String cover;
    public String description;
    public String lessonNum;
    public String subjectL1Title;
    public String subjectL2Title;
    public String teacherName;
    public String price;

}
