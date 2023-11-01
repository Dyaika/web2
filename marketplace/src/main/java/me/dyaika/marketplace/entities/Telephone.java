package me.dyaika.marketplace.entities;

import lombok.Data;

@Data
public class Telephone {
    private Long id;
    private String vendor;
    private Integer battery;
    private String sellernumber;
    private Double price;
    private String title;
    private String type;
}
