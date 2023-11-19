package me.dyaika.marketplace.db.repository;

import org.springframework.stereotype.Repository;
import me.dyaika.marketplace.db.model.Book;

@Repository
public interface BookRepository extends ProductRepository<Book> {
}