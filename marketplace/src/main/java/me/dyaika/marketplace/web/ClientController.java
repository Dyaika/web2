package me.dyaika.marketplace.web;

import me.dyaika.marketplace.db.repositories.ClientRepository;
import me.dyaika.marketplace.web.filters.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import me.dyaika.marketplace.db.model.UserRole;
import me.dyaika.marketplace.db.redis.RedisLoader;
import me.dyaika.marketplace.db.model.User;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientRepository clientRepository;
	private final RedisLoader redisLoader;

	@Autowired
	public ClientController(ClientRepository clientRepository, RedisLoader redisLoader) {
		this.clientRepository = clientRepository;
		this.redisLoader = redisLoader;
	}

	@GetMapping("")
	public List<User> getAllClients() {
		return clientRepository.findAll();
	}

	@GetMapping("/{id}")
	public User getClientById(@PathVariable Long id) {
		return clientRepository.getClientById(id);
	}

	@PostMapping("")
	public User createClient(@RequestBody User user) {
		clientRepository.createClient(user);
		return clientRepository.findByLogin(user.getLogin());
	}

	@PutMapping("/{id}")
	public User updateClient(@PathVariable Long id, @RequestBody User user, @RequestAttribute("user") Long userId) {
		if (!Objects.equals(userId, id)) {
			return null;
		}
		user.setId(id);
		clientRepository.updateClient(user);
		return clientRepository.getClientById(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable Long id, @RequestAttribute("user") Long userId) {
		if (Objects.equals(userId, id)) {
			clientRepository.deleteClient(id);
			return ResponseEntity.ok("");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
	}

	@AuthFilter(userRole = UserRole.CLIENT)
	@PutMapping("/{id}/becomeSeller")
	public ResponseEntity<?> becomeSeller(@PathVariable Long id, @RequestAttribute("user") Long userId,
										  @RequestHeader String Authorization) {
		if (!Objects.equals(userId, id)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong user");
		}
		User user = clientRepository.getClientById(id);
		user.setRole(UserRole.SELLER);
		clientRepository.updateClient(user);
		redisLoader.deleteTokenInformation(Authorization.replace("Bearer ", ""));
		return ResponseEntity.ok("");
	}

	@AuthFilter(userRole = UserRole.SELLER)
	@PutMapping("/{id}/becomeClient")
	public ResponseEntity<?> becomeClient(@PathVariable Long id, @RequestAttribute("user") Long userId,
										  @RequestHeader String Authorization) {
		if (!Objects.equals(userId, id)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong user");
		}
		User user = clientRepository.getClientById(id);
		user.setRole(UserRole.CLIENT);
		clientRepository.updateClient(user);
		redisLoader.deleteTokenInformation(Authorization.replace("Bearer ", ""));
		return ResponseEntity.ok("");
	}

	@AuthFilter
	@PutMapping("/{id}/becomeAdmin")
	public ResponseEntity<?> becomeAdmin(@PathVariable Long id, @RequestAttribute("user") Long userId,
										 @RequestHeader String Authorization) {
		if (!Objects.equals(userId, id)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong user");
		}
		User user = clientRepository.getClientById(id);
		user.setRole(UserRole.ADMIN);
		clientRepository.updateClient(user);
		redisLoader.deleteTokenInformation(Authorization.replace("Bearer ", ""));
		return ResponseEntity.ok("");
	}
}
