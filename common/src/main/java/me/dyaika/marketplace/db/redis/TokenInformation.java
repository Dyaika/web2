package me.dyaika.marketplace.db.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash("TokenInformation")
public class TokenInformation {
	Long id;
	String role;
}
