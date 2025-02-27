package com.example.grocerybookingapi.controller;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.grocerybookingapi.exception.DuplicateFieldException;
import com.example.grocerybookingapi.model.User;
import com.example.grocerybookingapi.service.AuthService;
import com.example.grocerybookingapi.util.ResponseWrapper;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	private static final Pattern DUPLICATE_FIELD_PATTERN = Pattern.compile("Duplicate entry '.*' for key '(.*?)'");

	@PostMapping("/register")
	public ResponseEntity<ResponseWrapper<Object>> register(@RequestBody User user) {
		try {
			User result = authService.registerUser(user);
			ResponseWrapper<Object> response = new ResponseWrapper<>(HttpStatus.CREATED.value(),
					"User registered successfully", result);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			String duplicateField = getDuplicateFieldName(e);
			if (duplicateField != null) {
				throw new DuplicateFieldException(
						"The " + duplicateField + " is already registered: " + user.getEmail());
			}
			throw e;
		}
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseWrapper<String>> login(@RequestBody Map<String, String> loginData) {
		String email = loginData.get("email");
		String password = loginData.get("password");

		try {
			String token = authService.login(email, password);
			ResponseWrapper<String> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Login successful", token);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (AuthenticationException e) {
			ResponseWrapper<String> response = new ResponseWrapper<>(HttpStatus.UNAUTHORIZED.value(),
					"Invalid credentials", null);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
	}

	private String getDuplicateFieldName(DataIntegrityViolationException e) {
		if (e.getCause() != null && e.getCause().getMessage() != null) {
			Matcher matcher = DUPLICATE_FIELD_PATTERN.matcher(e.getCause().getMessage());
			if (matcher.find()) {
				String keyName = matcher.group(1);
				// Map database constraint names to user-friendly field names
				switch (keyName) {
				case "users.email_unique": // Use actual constraint name in your DB schema
					return "email";
				case "users.phone_number_unique":
					return "phone number";
				// Add other unique constraints as needed
				default:
					return "field"; // Generic fallback
				}
			}
		}
		return null;
	}
}
