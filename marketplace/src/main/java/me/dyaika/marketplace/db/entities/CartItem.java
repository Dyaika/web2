package me.dyaika.marketplace.db.entities;

import lombok.Data;

@Data
public class CartItem {
    private Long id;
    private Integer price;

    private String sellernumber;
    private String type;
    private String title;
    private Integer amount;
}
