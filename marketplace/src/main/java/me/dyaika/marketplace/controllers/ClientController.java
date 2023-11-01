package me.dyaika.marketplace.controllers;

import me.dyaika.marketplace.entities.Client;
import me.dyaika.marketplace.repositories.ClientRepository;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/clients")
public class ClientController {
    private final ClientRepository repository;

    public ClientController(ClientRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Long createClient(@RequestBody Client book){
        return repository.createClient(book);
    }

    @GetMapping
    public Client getClient(@RequestParam long id){
        return repository.getClient(id);
    }

    @PutMapping
    public void updateClient(@RequestBody Client book){
        repository.updateClient(book);
    }

    @DeleteMapping
    public void deleteClient(@RequestParam long id){
        repository.deleteClient(id);
    }

}
