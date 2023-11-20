package me.dyaika.marketplace.db.entities;
import lombok.Data;

@Data
public class Item {
    private Long id;
    private Integer price;
    private Long sellernumber;
    private String type;
    private String title;
}