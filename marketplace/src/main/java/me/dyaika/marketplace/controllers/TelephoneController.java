package me.dyaika.marketplace.controllers;

import me.dyaika.marketplace.entities.Telephone;
import me.dyaika.marketplace.repositories.TelephoneRepository;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/telephone")
public class TelephoneController {

    private final TelephoneRepository repository;

    public TelephoneController(TelephoneRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Long createTelephone(@RequestBody Telephone book){
        return repository.createTelephone(book);
    }

    @GetMapping
    public Telephone getTelephone(@RequestParam long id){
        return repository.getTelephone(id);
    }

    @PutMapping
    public void updateTelephone(@RequestBody Telephone book){
        repository.updateTelephone(book);
    }

    @DeleteMapping
    public void deleteTelephone(@RequestParam long id){
        repository.deleteTelephone(id);
    }
}
