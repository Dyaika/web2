package me.dyaika.marketplace.controllers;

import me.dyaika.marketplace.entities.WashingMachine;
import me.dyaika.marketplace.repositories.WashingMachineRepository;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/washingmachine")
@RestController
public class WashingMachineController {

    private final WashingMachineRepository repository;

    public WashingMachineController(WashingMachineRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Long createWashingMachine(@RequestBody WashingMachine book){
        return repository.createWashingMachine(book);
    }

    @GetMapping
    public WashingMachine getWashingMachine(@RequestParam long id){
        return repository.getWashingMachine(id);
    }

    @PutMapping
    public void updateWashingMachine(@RequestBody WashingMachine book){
        repository.updateWashingMachine(book);
    }

    @DeleteMapping
    public void deleteWashingMachine(@RequestParam long id){
        repository.deleteWashingMachine(id);
    }
}
