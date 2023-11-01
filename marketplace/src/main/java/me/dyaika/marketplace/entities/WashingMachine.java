package me.dyaika.marketplace.entities;

import lombok.Data;

@Data
public class WashingMachine {
    private Long id;
    private String vendor;
    private Integer volume;
    private String sellernumber;
    private Double price;
    private String title;
    private String type;
}
