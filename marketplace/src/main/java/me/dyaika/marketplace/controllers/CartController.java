package me.dyaika.marketplace.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.dyaika.marketplace.entities.Book;
import me.dyaika.marketplace.entities.CartItem;
import me.dyaika.marketplace.repositories.CartRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Cart", description = "User's cart")
@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartRepository repository;

    public CartController(CartRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/{clientId}/add")
    public Boolean addToCart(@PathVariable Long clientId, @RequestParam Long itemId){
        return repository.addToCart(clientId, itemId);
    }

    @ApiOperation("Cart content")
    @GetMapping("/{clientId}")
    public List<CartItem> getCart(@PathVariable Long clientId){
        return repository.getCart(clientId);
    }

    @PutMapping("/{clientId}/update")
    public Boolean updateAmountInCart(@PathVariable Long clientId, @RequestParam Long itemId, @RequestParam Integer amount){
        return repository.updateAmountInCart(clientId, itemId, amount);
    }

    @DeleteMapping("/{clientId}/remove")
    public void removeFromCart(@PathVariable Long clientId, @RequestParam Long itemId){
        repository.removeFromCart(clientId, itemId);
    }

    @DeleteMapping("/{clientId}/order")
    public String makeOrder(@PathVariable Long clientId){
        Integer sum = repository.makeOrder(clientId);
        if (sum == null){
            return "Часть корзины недоступна";
        } else if (sum == 0) {
            return "Нет товаров в корзине";
        } else {
            return "Стоимость заказа " + sum + " рублей.";
        }
    }
}
