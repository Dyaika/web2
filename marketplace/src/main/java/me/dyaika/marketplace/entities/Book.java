package me.dyaika.marketplace.entities;

import lombok.Data;

@Data
public class Book {
    private Long id;

    private String author;
    private String sellernumber;
    private String type;
    private Double price;
    private String title;
}