package me.dyaika.marketplace.db.model;

import jakarta.persistence.*;
import lombok.Data;

import lombok.EqualsAndHashCode;


@Entity
@Table(name = "telephones")
@Data
@EqualsAndHashCode(callSuper = true)
public class Telephone extends Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String manufacturer;
	private String model;
}
