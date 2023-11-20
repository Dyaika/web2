package me.dyaika.marketplace.web;

import com.google.gson.Gson;
import me.dyaika.marketplace.db.redis.RedisSaver;
import me.dyaika.marketplace.db.repositories.ClientRepository;
import me.dyaika.marketplace.services.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import me.dyaika.marketplace.db.redis.TokenInformation;
import me.dyaika.marketplace.db.model.User;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final JwtTokenProvider jwtTokenProvider;
	private final ClientRepository clientRepository;
	private final RedisSaver redisSaver;

	@Autowired
	public AuthController(JwtTokenProvider jwtTokenProvider, ClientRepository clientRepository, RedisSaver redisSaver) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.clientRepository = clientRepository;
		this.redisSaver = redisSaver;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
		User user = clientRepository.findByLogin(username);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		if (Objects.equals(user.getPassword(), password)) {
			String token = jwtTokenProvider.generateToken(user);
			redisSaver.saveTokenInformation(token, new TokenInformation(user.getId(), user.getRole().toString()));
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
	}

	@GetMapping("/validate")
	public ResponseEntity<?> validate(@RequestHeader("Authorization") String authorizationHeader) {
		if (jwtTokenProvider.validateToken(extractToken(authorizationHeader))) {
			Long id = jwtTokenProvider.getUserIdFromToken(extractToken(authorizationHeader));
			User user = clientRepository.getClientById(id);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
			return ResponseEntity.ok(user.getRole().toString());
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("There are no token");
	}

	@GetMapping("/check")
	public ResponseEntity<?> check(@RequestHeader("Authorization") String authorizationHeader) {
		if (jwtTokenProvider.validateToken(extractToken(authorizationHeader))) {
			TokenInformation token = redisSaver.getTokenInformation(extractToken(authorizationHeader));
			try {
				return ResponseEntity.ok(new Gson().toJson(token));
			} catch (NullPointerException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized");
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("There are no token");
	}

	private String extractToken(String authorizationHeader) {
		return authorizationHeader.replace("Bearer ", "");
	}
}
