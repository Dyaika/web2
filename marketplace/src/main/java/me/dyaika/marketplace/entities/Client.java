package me.dyaika.marketplace.entities;

import lombok.Data;

@Data
public class Client {
    private Long id;

    private String name;
    private String email;
    private String login;
    private String password;
}
