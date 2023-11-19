package me.dyaika.marketplace.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import me.dyaika.marketplace.db.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
