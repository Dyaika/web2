package me.dyaika.marketplace.web.filters;

import me.dyaika.marketplace.db.model.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthFilter {
	UserRole userRole() default UserRole.CLIENT;
	long id() default -1L;
}