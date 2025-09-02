package com.csi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
@ToString
@Getter
@Setter
public class Volunteer {
    //志愿者活动申请ID（自增）
    private int id;
    //申请人ID
    private List<User> users;
    //申请日期
    @DateTimeFormat(pattern = "yyyy-MM-dd mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Date applyDate;
    //活动ID
    private int activityId;
    //状态: 待审核/已通过/已拒绝 0是待审核，1是已通过，2是已拒绝
    private int status;
    //审核人
    private int employeeId;
    //审核日期
    @DateTimeFormat(pattern = "yyyy-MM-dd mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Date auditDate;
    // 驳回原因
    private String rejectReason;
    private User user;
    private int userId;
    private Activity activity;
//    //活动名称
//    private String activityName;
//    //活动开始日期
//    private String activeStart;
//    //活动截至时间（报名结束时间）
//    private String activeEnd;
      //审核人姓名
    private String employeeName;


}
