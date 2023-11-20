package me.dyaika.marketplace.web;

import me.dyaika.marketplace.db.repository.*;
import me.dyaika.marketplace.web.filters.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import me.dyaika.marketplace.db.model.UserRole;
import me.dyaika.marketplace.db.model.Book;
import me.dyaika.marketplace.db.model.Product;
import me.dyaika.marketplace.db.model.Telephone;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductRepository<Product> productRepository;
	private final BookRepository bookRepository;
	private final TelephoneRepository telephoneRepository;
	private final ItemsRepository itemsRepository;

	@Autowired
	public ProductController(ProductRepository<Product> productRepository, BookRepository bookRepository,
							 TelephoneRepository telephoneRepository, ItemsRepository itemsRepository) {
		this.productRepository = productRepository;
		this.bookRepository = bookRepository;
		this.telephoneRepository = telephoneRepository;
		this.itemsRepository = itemsRepository;
	}

	@GetMapping("/all")
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@PostMapping("/books")
	@AuthFilter(userRole = UserRole.SELLER)
	public Book createBook(@RequestBody Book book, @RequestAttribute Long user) {
		book.setSeller(user);
		return bookRepository.save(book);
	}

	@PostMapping("/telephones")
	@AuthFilter(userRole = UserRole.SELLER)
	public Telephone createTelephone(@RequestBody Telephone telephone, @RequestAttribute Long user) {
		telephone.setSeller(user);
		return telephoneRepository.save(telephone);
	}

	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id) {
		return productRepository.findById(id).orElse(null);
	}

	@GetMapping("/books/{id}")
	public Book getBookById(@PathVariable Long id) {
		return itemsRepository.getBook(id);
	}

	@GetMapping("/telephones/{id}")
	public Telephone getTelephoneById(@PathVariable Long id) {
		return telephoneRepository.findById(id).orElse(null);
	}

	@PutMapping("/books/{id}")
	@AuthFilter(userRole = UserRole.SELLER)
	public Book updateBook(@PathVariable Long id, @RequestBody Book book, @RequestAttribute Long user) {
		if (bookRepository.existsById(id)) {
			Book b = bookRepository.getReferenceById(id);
			if (b.getSeller() == user) {
				book.setId(id);
				book.setSeller(user);
				return bookRepository.save(book);
			}
		}
		return null;
	}

	@PutMapping("/telephones/{id}")
	@AuthFilter(userRole = UserRole.SELLER)
	public Telephone updateTelephone(@PathVariable Long id, @RequestBody Telephone telephone,
									 @RequestAttribute Long user) {
		if (telephoneRepository.existsById(id)) {
			Telephone b = telephoneRepository.getReferenceById(id);
			if (b.getSeller() == user) {
				telephone.setId(id);
				telephone.setSeller(user);
				return telephoneRepository.save(telephone);
			}
		}
		return null;
	}

	@DeleteMapping("/books/{id}")
	@AuthFilter(userRole = UserRole.SELLER)
	public void deleteBook(@PathVariable Long id, @RequestAttribute Long user) {
		if (bookRepository.existsById(id)) {
			Book b = bookRepository.getReferenceById(id);
			if (b.getSeller() == user) {
				bookRepository.deleteById(id);
			}
		}
	}

	@DeleteMapping("/telephones/{id}")
	@AuthFilter(userRole = UserRole.SELLER)
	public void deleteTelephone(@PathVariable Long id, @RequestAttribute Long user) {
		if (telephoneRepository.existsById(id)) {
			Telephone b = telephoneRepository.getReferenceById(id);
			if (b.getSeller() == user) {
				telephoneRepository.deleteById(id);
			}
		}
	}
}