package me.dyaika.marketplace.web;

import me.dyaika.marketplace.db.entities.Item;
import me.dyaika.marketplace.db.repositories.ItemRepository;
import me.dyaika.marketplace.web.filters.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import me.dyaika.marketplace.db.model.UserRole;
import me.dyaika.marketplace.db.model.Book;
import me.dyaika.marketplace.db.model.Product;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ItemRepository itemsRepository;

	@Autowired
	public ProductController(ItemRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}

	@GetMapping("/all")
	public List<Item> getAllProducts() {
		return itemsRepository.findAll();
	}

	@PostMapping("/add")
	@AuthFilter(userRole = UserRole.SELLER)
	public Item createItem(@RequestBody Item item, @RequestAttribute Long user) {
		item.setSellernumber(user);
		return itemsRepository.getById(itemsRepository.createItem(item));
	}

	@GetMapping("/{id}")
	public Item getProductById(@PathVariable Long id) {
		return itemsRepository.getById(id);
	}

	@PutMapping("/update/{id}")
	@AuthFilter(userRole = UserRole.SELLER)
	public Item updateItem(@PathVariable Long id, @RequestBody Item newItem, @RequestAttribute Long user) {
		Item item = itemsRepository.getById(id);
		if (item.getSellernumber() == user) {
			newItem.setId(id);
			newItem.setSellernumber(user);
			itemsRepository.update(newItem);
		}
		return itemsRepository.getById(newItem.getId());
	}

	@DeleteMapping("/remove/{id}")
	@AuthFilter(userRole = UserRole.SELLER)
	public void deleteBook(@PathVariable Long id, @RequestAttribute Long user) {
		Item b = itemsRepository.getById(id);
		if (b.getSellernumber() == user) {
			itemsRepository.remove(id);
		}
	}
}