package com.example.demo.domain.account;

import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.domain.account.request.LogInRequestDto;
import com.example.demo.domain.account.request.SignUpRequestDto;
import com.example.demo.global.dto.response.BackpacResponseBody;
import com.example.demo.global.dto.response.BackpacResponseData;
import com.example.demo.global.dto.response.DataResponseBody;
import com.example.demo.global.dto.response.ErrorData;
import com.example.demo.global.dto.response.SimpleResponseBody;

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
		
		BackpacResponseData responseData = service.signup(param);
		
		if (responseData instanceof ErrorData) {
			return DataResponseBody.getFailBody("회원가입", responseData);
		}
		return SimpleResponseBody.getSuccessBody("회원가입");
	}

	/**
	 * 로그인
	 * @return
	 * @throws Exception 
	 */
	@PostMapping("/login")
	public BackpacResponseBody signIn(HttpServletRequest request, @RequestBody @Valid LogInRequestDto param) throws Exception {
	    
		BackpacResponseData responseData = service.login(param);
		
		if (responseData instanceof ErrorData) {
            return DataResponseBody.getFailBody("로그인", responseData);
		}
		
	    // 로그인 세션 처리
	    request.getSession().setAttribute("login_email", param.getEmail());
	    
        return SimpleResponseBody.getSuccessBody("로그인");
	}

	/**
	 * 로그아웃
	 * @return
	 */
	@GetMapping("/logout")
	public BackpacResponseBody logOut(HttpServletRequest request) {
	    
	    // 세션 아웃 처리
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    return SimpleResponseBody.getSuccessBody("로그아웃");
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
