package com.csi.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDTO {
     //后端管理的修改表单字段
    //房间ID自增主键
    private int id;
    //房型
    private String typeId;
    //入住人数
    private int intoNumber;
    //最大入住人数
    private  int maxNumber;



}
