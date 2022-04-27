package com.example.demo.domain.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.global.dto.response.BackpacResponseBody;

@RestController
@RequestMapping("/account")
public class AccountController {

	@GetMapping("/signup")
	public BackpacResponseBody signUp() {
		return null;
	}

	@GetMapping("/signin")
	public BackpacResponseBody signIn() {
		return null;
	}

	@GetMapping("/logout")
	public BackpacResponseBody logOut() {
		return null;
	}

	@GetMapping("/me")
	public BackpacResponseBody me() {
		return null;
	}
	
}
