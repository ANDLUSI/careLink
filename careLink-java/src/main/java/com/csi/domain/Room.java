package com.csi.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Room {
    //房间ID自增主键
    private int id;
    //房型
    private String typeId;
    //入住人数
    private int intoNumber;
    //最大入住人数
    private  int maxNumber;

    //状态: 空闲/占用(0空闲 1占用)
    private Resident resident;
    private Charge charge;

}
