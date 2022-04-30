package com.example.demo.domain.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.domain.account.request.SignUpRequestDto;
import com.example.demo.global.dto.response.BackpacResponseData;
import com.example.demo.global.dto.response.ErrorData;
import com.example.demo.global.dto.response.SuccessData;

@WebMvcTest(controllers = AccountService.class)
public class AccountServiceTest {

    @Autowired
    private AccountService service;

    @MockBean
    private AccountRepository accountRepository;
    
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

	    when(accountRepository.findByEmail(any())).thenReturn(null);
	    
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
        when(accountRepository.findByEmail(any())).thenReturn(accountMock);
        
        // then
        BackpacResponseData signupResult = service.signup(param);
        assertThat(signupResult).isExactlyInstanceOf(ErrorData.class);
    }
}
