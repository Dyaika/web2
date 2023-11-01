package me.dyaika.marketplace.controllers;

import me.dyaika.marketplace.entities.Book;
import me.dyaika.marketplace.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {

    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Long createBook(@RequestBody Book book){
        return repository.createBook(book);
    }

    @GetMapping
    public Book getBook(@RequestParam long id){
        return repository.getBook(id);
    }

    @PutMapping
    public void updateBook(@RequestBody Book book){
        repository.editBook(book);
    }

    @DeleteMapping
    public void deleteBook(@RequestParam long id){
        repository.deleteBook(id);
    }
}