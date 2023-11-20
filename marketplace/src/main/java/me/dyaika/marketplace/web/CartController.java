package me.dyaika.marketplace.web;

import me.dyaika.marketplace.db.entities.CartItem;
import me.dyaika.marketplace.db.repositories.CartRepository;
import me.dyaika.marketplace.db.repositories.ClientRepository;
import me.dyaika.marketplace.web.filters.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart/{user_id}")
public class CartController {
	private final CartRepository cartRepository;

	@Autowired
	public CartController(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}

	@GetMapping("")
	@AuthFilter
	public List<CartItem> viewCart(@PathVariable Long user_id, @RequestAttribute Long user) {
		if (user_id != user) {
			return null;
		}
		return cartRepository.getCart(user_id);
	}


	@PostMapping("/add")
	@AuthFilter
	public ResponseEntity<String> addToCart(@PathVariable Long user_id, @RequestParam Long itemId,
											@RequestAttribute Long user) {
		if (user_id != user) {
			return null;
		}
		if (!cartRepository.addToCart(user_id, itemId)){
			return ResponseEntity.badRequest().body("Product was not added to cart.");
		}
		return ResponseEntity.ok("Product added to cart successfully.");
	}

	@DeleteMapping("/remove/{cartItemId}")
	@AuthFilter
	public ResponseEntity<String> removeFromCart(@PathVariable Long cartItemId, @PathVariable Long user_id,
												 @RequestAttribute Long user) {
		if (user_id != user) {
			return null;
		}
		cartRepository.removeFromCart(user_id, cartItemId);
		return ResponseEntity.ok("Product removed from cart successfully.");
	}

	@PostMapping("/checkout")
	@AuthFilter
	public ResponseEntity<String> checkout(@PathVariable Long user_id, @RequestAttribute Long user) {
		if (user_id != user) {
			return null;
		}
		Integer sum = cartRepository.makeOrder(user_id);
		if (sum == null){
			return ResponseEntity.badRequest().body("Not all items in cart are available.");
		} else if (sum == 0) {
			return ResponseEntity.badRequest().body("Empty cart.");
		} else {
			return ResponseEntity.badRequest().body("Total order cost: " + sum + " RUB.");
		}
	}
}