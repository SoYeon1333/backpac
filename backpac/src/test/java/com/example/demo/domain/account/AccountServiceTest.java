package com.example.demo.domain.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.domain.account.request.LogInRequestDto;
import com.example.demo.domain.account.request.SignUpRequestDto;
import com.example.demo.global.dto.response.BackpacResponseData;
import com.example.demo.global.dto.response.ErrorData;
import com.example.demo.global.dto.response.SuccessData;

@WebMvcTest(controllers = AccountService.class)
public class AccountServiceTest {

    @Autowired
    private AccountService service;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @MockBean
    private AccountRepository repository;
    
    @Test
    @DisplayName("회원가입 성공")
    public void signupSuccess() throws Exception {

    	SignUpRequestDto param = new SignUpRequestDto();
    	param.setName("testName");
    	param.setEmail("testRegNo@email.com");
    	param.setNickname("testnckname");
    	param.setPassword("12345678901Aa!");
    	param.setPhone("1012345678");
    	param.setSex("M");
    	param.setType("user");

	    when(repository.findTop1ByEmailOrNickname(any(), any())).thenReturn(null);
	    
	    // then
	    BackpacResponseData signupResult = service.signup(param);
        assertThat(signupResult).isExactlyInstanceOf(SuccessData.class);
    }

    @Test
    @DisplayName("회원가입 실패(이미 가입된 아이디)")
    public void signupFail() throws Exception {

        SignUpRequestDto param = new SignUpRequestDto();
        param.setName("testName");
        param.setEmail("testRegNo@email.com");
        param.setNickname("testnckname");
        param.setPassword("12345678901Aa!");
        param.setPhone("1012345678");
        param.setSex("M");
        param.setType("user");

        // 회원가입 대상
        AccountEntity accountMock = new AccountEntity();
        accountMock.setEmail("testRegNo@email.com");
        accountMock.setNickname("testnckname@email.com");
        when(repository.findTop1ByEmailOrNickname(any(), any())).thenReturn(accountMock);
        
        // then
        BackpacResponseData signupResult = service.signup(param);
        assertThat(signupResult).isExactlyInstanceOf(ErrorData.class);
    }
    
    @Test
    @DisplayName("로그인성공") 
    public void loginSuccess() throws Exception {

        LogInRequestDto param = new LogInRequestDto();
        param.setEmail("test@email.com");
        param.setPassword("passTest");
        
        // 유저 정보 조회
        AccountEntity accountMock = new AccountEntity();
        accountMock.setEmail("test@email.com");
        accountMock.setPassword(passwordEncoder.encode("passTest"));

        when(repository.findByEmail(any())).thenReturn(accountMock);
        
        // then
        BackpacResponseData loginResult = (BackpacResponseData)service.login(param);
        
        assertThat(loginResult).isInstanceOf(SuccessData.class);
    }
    
    @Test
    @DisplayName("로그인실패(잘못된 아이디)") 
    public void loginFail() throws Exception {

        LogInRequestDto param = new LogInRequestDto();
        param.setEmail("test@email.com");
        param.setPassword("passTest");
        
        // 유저 정보 조회
        AccountEntity accountMock = new AccountEntity();
        accountMock.setEmail("test@email.com");
        accountMock.setPassword(passwordEncoder.encode("passTest"));

        when(repository.findByEmail(any())).thenReturn(null);
        
        // then
        ErrorData loginResult = (ErrorData)service.login(param);
        assertThat(loginResult.getErrorMessage()).isEqualTo("존재하지 않는 아이디입니다.");
    }
    
    @Test
    @DisplayName("로그인실패(잘못된 비밀번호)") 
    public void loginFail2() throws Exception {

        LogInRequestDto param = new LogInRequestDto();
        param.setEmail("test@email.com");
        param.setPassword("passTest");
        
        // 유저 정보 조회
        AccountEntity accountMock = new AccountEntity();
        accountMock.setEmail("test@email.com");
        accountMock.setPassword(passwordEncoder.encode("passTestError"));

        when(repository.findByEmail(any())).thenReturn(accountMock);
        
        // then
        ErrorData loginResult = (ErrorData)service.login(param);
        assertThat(loginResult.getErrorMessage()).isEqualTo("비밀번호를 확인해 주세요.");
    }
}
