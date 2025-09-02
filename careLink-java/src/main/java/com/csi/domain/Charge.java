package com.csi.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Charge {

    private Integer id;

    private String item;

    private double price;

    private String description;

    private String type;
}
