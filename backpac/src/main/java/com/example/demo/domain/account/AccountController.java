package com.example.demo.domain.account;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.account.request.LogInRequestDto;
import com.example.demo.domain.account.request.SignUpRequestDto;
import com.example.demo.global.dto.response.BackpacResponseBody;
import com.example.demo.global.dto.response.BackpacResponseData;
import com.example.demo.global.dto.response.SimpleResponseBody;
import com.example.demo.global.dto.response.SuccessData;

@RestController
@RequestMapping("/account")
public class AccountController {

	private AccountService service;
	
	public AccountController(AccountService service) {
		this.service = service;
	}
	
	/**
	 * 회원 가입
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/signup")
	public BackpacResponseBody signUp(@RequestBody @Valid SignUpRequestDto param) throws Exception {
		service.signup(param);
		return SimpleResponseBody.getSuccessBody("회원가입");
	}

	/**
	 * 로그인
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/login")
	public BackpacResponseBody signIn(@RequestBody @Valid LogInRequestDto param) throws Exception {
		BackpacResponseData responseData = service.login(param);
		
		if (responseData instanceof SuccessData) {
			return SimpleResponseBody.getSuccessBody("로그인");
		}
		else {
			return SimpleResponseBody.getFailBody("로그인");
		}
	}

	/**
	 * 로그아웃
	 * @return
	 */
	@GetMapping("/logout")
	public BackpacResponseBody logOut() {
		return null;
	}

	/**
	 * 단일 회원 조회
	 * @return
	 */
	@GetMapping("/me")
	public BackpacResponseBody me() {
		return null;
	}
	
}
