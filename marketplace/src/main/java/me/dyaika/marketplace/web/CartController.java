package me.dyaika.marketplace.web;

import me.dyaika.marketplace.web.DTO.ProductCount;
import me.dyaika.marketplace.web.filters.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import me.dyaika.marketplace.db.repository.CartItemRepository;
import me.dyaika.marketplace.db.repository.CartRepository;
import me.dyaika.marketplace.db.repository.UserRepository;
import me.dyaika.marketplace.db.repository.ProductRepository;
import me.dyaika.marketplace.db.model.Cart;
import me.dyaika.marketplace.db.model.CartItem;
import me.dyaika.marketplace.db.model.Product;

import java.util.List;

@RestController
@RequestMapping("/api/cart/{user_id}")
public class CartController {
	private final CartRepository cartRepository;
	private final ProductRepository<Product> productRepository;
	private final CartItemRepository cartItemRepository;
	private final UserRepository userRepository;

	@Autowired
	public CartController(CartRepository cartRepository, ProductRepository<Product> productRepository,
						  CartItemRepository cartItemRepository, UserRepository userRepository) {
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.cartItemRepository = cartItemRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/all")
	public List<Cart> all(@PathVariable Long user_id) {
		return cartRepository.findAll();
	}

	@GetMapping
	@AuthFilter
	public List<CartItem> viewCart(@PathVariable Long user_id, @RequestAttribute Long user) {
		if (user_id != user) {
			return null;
		}
		Cart cart = cartRepository.findById(user_id).orElse(null);
		if (cart == null) {
			cart = new Cart();
			cart.setUser(userRepository.getReferenceById(user_id));
			cartRepository.save(cart);
		}
		return cart.getItems();
	}


	@PostMapping("/add")
	@AuthFilter
	public ResponseEntity<String> addToCart(@PathVariable Long user_id, @RequestBody ProductCount item,
											@RequestAttribute Long user) {
		if (user_id != user) {
			return null;
		}
		Product product = productRepository.findById(item.getProductId()).orElse(null);
		if (product == null) {
			return ResponseEntity.badRequest().body("Product does not exist");
		}
		if (product.getCountAvailable() < item.getCount()) {
			return ResponseEntity.badRequest().body("There are no so much product");
		}
		Cart cart = cartRepository.findById(user_id).orElse(null);
		if (cart == null) {
			cart = new Cart();
			cart.setId(user_id);
			cart = cartRepository.save(cart);
		}
		CartItem cartItem = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(item.getProductId())).findAny().orElse(null);
		if (cartItem == null) {
			cartItem = new CartItem();
			cartItem.setCart(cart);
			cartItem.setProduct(product);
		}
		cartItem.setCount(item.getCount());
		cartItemRepository.save(cartItem);
		return ResponseEntity.ok("Product added to cart successfully.");
	}

	@DeleteMapping("/remove/{cartItemId}")
	@AuthFilter
	public ResponseEntity<String> removeFromCart(@PathVariable Long cartItemId, @PathVariable Long user_id,
												 @RequestAttribute Long user) {
		if (user_id != user) {
			return null;
		}
		cartItemRepository.deleteById(cartItemId);
		return ResponseEntity.ok("Product removed from cart successfully.");
	}

	@PostMapping("/checkout")
	@AuthFilter
	public ResponseEntity<String> checkout(@PathVariable Long user_id, @RequestAttribute Long user) {
		if (user_id != user) {
			return null;
		}
		Cart cart = cartRepository.findById(user_id).orElse(null);
		if (cart != null) {
			boolean moreThanExist = false;
			for (CartItem item : cart.getItems()) {
				Product p = productRepository.findById(item.getProduct().getId()).orElse(null);
				if (p == null || item.getCount() > p.getCountAvailable()) {
					moreThanExist = true;
				}
			}
			if (moreThanExist) {
				return ResponseEntity.badRequest().body("There are not enough products");
			}
			for (CartItem item : cart.getItems()) {
				Product p = productRepository.findById(item.getProduct().getId()).get();
				p.setCountAvailable(p.getCountAvailable() - item.getCount());
				productRepository.save(p);
			}
			cartItemRepository.deleteAllByCartId(cart.getId());
			return ResponseEntity.ok("Order placed successfully. Cart cleared.");
		}
		return ResponseEntity.badRequest().body("Cart not found.");
	}
}