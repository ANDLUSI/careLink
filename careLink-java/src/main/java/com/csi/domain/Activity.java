package com.csi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Activity {
    //活动ID自增主键
    private int id;
    //活动名称
    private String name;
    //报名开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Date applicationStart;
    //活动开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Date activeStart;
    //活动截至时间（报名结束时间）
    @DateTimeFormat(pattern = "yyyy-MM-dd mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Date activeEnd;
    //活动地点
    private String location;
    //活动描述
    private String description;
    //活动状态（0是未开始，1是进行中，2是已结束）
    private int activeStatus;
//    private Volunteer volunteer;
//    private User user;
    //
//    private List<User> users;

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", applicationStart='" + applicationStart + '\'' +
                ", activeStart='" + activeStart + '\'' +
                ", activeEnd='" + activeEnd + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", activeStatus=" + activeStatus +
                '}';
    }
}
