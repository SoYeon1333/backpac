package com.example.demo.domain.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.account.request.LogInRequestDto;
import com.example.demo.domain.account.request.SignUpRequestDto;
import com.example.demo.global.dto.response.BackpacResponseBody;
import com.example.demo.global.dto.response.BackpacResponseData;
import com.example.demo.global.dto.response.DataResponseBody;
import com.example.demo.global.dto.response.ErrorData;
import com.example.demo.global.dto.response.SimpleResponseBody;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/account")
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
	@ApiOperation(value="회원가입", notes="회원가입 API 입니다.")
	    @ApiImplicitParams({
	        @ApiImplicitParam(name = "Sample", value = 
	                "{\"email\": \"string@email.test\","
	                + "\"name\": \"string\","
	                + "\"nickname\": \"string\","
	                + "\"password\": \"passwor123st!D\","
	                + "\"phone\": \"1234\","
	                + "\"sex\": \"Male\","
	                + "\"type\": \"user\"}",
	                required = true, dataTypeClass = JSONObject.class, paramType = "body")
	    })
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
    @ApiOperation(value="로그인", notes="로그인 API 입니다.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Sample", value = 
                "{\"email\": \"string@email.test\","
                + "\"password\": \"passwor123st!D\"}",
                required = true, dataTypeClass = JSONObject.class, paramType = "body")
    })
	@PostMapping("/login")
	public BackpacResponseBody signIn(HttpServletRequest request, @RequestBody @Valid LogInRequestDto param) throws Exception {
	    
		BackpacResponseData responseData = service.login(param);
		
		if (responseData instanceof ErrorData) {
            return DataResponseBody.getFailBody("로그인", responseData);
		}
		
	    // 로그인 세션 처리
	    request.getSession().setAttribute("login_email", param.getEmail());
	    
        return DataResponseBody.getSuccessBody("로그인", responseData);
	}

	/**
	 * 로그아웃
	 * @return
	 */
    @ApiOperation(value="로그아웃", notes="로그아웃 API 입니다.")
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
	 * @throws Exception 
	 */
    @ApiOperation(value="단일 회원 조회", notes="단일 회원 조회 API 입니다.")
	@GetMapping("/me")
	public BackpacResponseBody me(HttpServletRequest request) throws Exception {
	    
	    if (request.getSession(false) == null) {
            return DataResponseBody.getFailBody("단일 회원 조회", ErrorData.getErrorData("로그인이 필요한 기능입니다."));
	    }
	    
        String email = request.getSession().getAttribute("login_email").toString();
		return DataResponseBody.getSuccessBody("단일 회원 조회", service.me(email));
	}
	
}
