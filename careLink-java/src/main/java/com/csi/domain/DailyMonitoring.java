package com.csi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 每日健康检测
 */
@Getter
@Setter
@ToString
public class DailyMonitoring {

    private Integer id;
    private int  residentId; //老人id
    private String residentName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "UTC")
    private Date monitoringDate;
    private double bloodPressure; //血压
    private double bloodSugar; //血糖
    private double heartRate; //心率
    private double temperature; //体温
    private String notes;

}
