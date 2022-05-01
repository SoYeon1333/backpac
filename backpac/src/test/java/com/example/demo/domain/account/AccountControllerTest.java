package com.example.demo.domain.account;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.domain.account.request.LogInRequestDto;
import com.example.demo.domain.account.request.SignUpRequestDto;
import com.example.demo.global.dto.response.SuccessData;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AccountController.class)
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

    @Autowired
	MessageSource messageSource;
    
    @MockBean
    private AccountService service;
    
	@Test
	@Order(1)
	@DisplayName("회원가입 성공")
	public void signupSuccess() throws Exception {		

		// given    
		SignUpRequestDto user = new SignUpRequestDto();
    	user.setName("testName");
    	user.setEmail("testRegNo@email.com");
    	user.setNickname("testnckname");
    	user.setPassword("12345678901Aa!");
    	user.setPhone("1012345678");
    	user.setSex("M");
    	user.setType("user");

        String json = new ObjectMapper().writeValueAsString(user);

        //when
        ResultActions resultActions = mockMvc.perform(post("/account/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print());

        //then
        resultActions
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.status", is("success")))
                 .andDo(print());
	}
	
	@Test
	@Order(2)
	@DisplayName("회원가입 실패(파라미터 누락)")
	public void signupFailByMissingParam() throws Exception {
		
		@SuppressWarnings("serial")
		List<Map<String, Object>> signupParams = List.of(
			// 이름 시작 -----------------
			// 이름 빈문자열
			  new HashMap<String, Object>(){{put("name", ""); 
			  								 put("password", "12345678901Aa!"); 
			  								 put("email", "testRegNo@email.com"); 
			  								 put("nickname", "testnickname"); 
			  								 put("phone", "1012345678"); 
			  								 put("message", messageSource.getMessage("NotBlank.signUpRequestDto.name", null, null));}}
			// 이름에 포함될 수 없는 문자
			, new HashMap<String, Object>(){{put("name", "12"); 
			  								 put("password", "12345678901Aa!"); 
			  								 put("email", "testRegNo@email.com"); 
			  								 put("nickname", "testnickname"); 
			  								 put("phone", "1012345678"); 
			  								 put("message", messageSource.getMessage("Pattern.signUpRequestDto.name", null, null));}}
			// 이름 최대 길이 초과
			, new HashMap<String, Object>(){{put("name", "qwertywerttywertywerqqwe"); 
			  								 put("password", "12345678901Aa!"); 
			  								 put("email", "testRegNo@email.com"); 
			  								 put("nickname", "testnickname"); 
			  								 put("phone", "1012345678"); 
			  								 put("message", messageSource.getMessage("Size.signUpRequestDto.name", null, null));}}
			// 별명 시작 -----------------
			// 별명 빈문자열
			, new HashMap<String, Object>(){{put("name", "testName"); 
			  								 put("password", "12345678901Aa!"); 
			  								 put("email", "testRegNo@email.com"); 
			  								 put("nickname", ""); 
			  								 put("phone", "1012345678"); 
			  								 put("message", messageSource.getMessage("NotBlank.signUpRequestDto.nickname", null, null));}}
			// 별명에 포함될 수 없는 문자
			, new HashMap<String, Object>(){{put("name", "testName"); 
			  								 put("password", "12345678901Aa!"); 
			  								 put("email", "testRegNo@email.com"); 
			  								 put("nickname", "QWER"); 
			  								 put("phone", "1012345678"); 
			  								 put("message", messageSource.getMessage("Pattern.signUpRequestDto.nickname", null, null));}}
			// 별명 최대 길이 초과
			, new HashMap<String, Object>(){{put("name", "testName"); 
			  								 put("password", "12345678901Aa!"); 
			  								 put("email", "testRegNo@email.com"); 
			  								 put("nickname", "testnicknametestnicknametestnicknametestnicknametestnicknametestnickname"); 
			  								 put("phone", "1012345678"); 
			  								 put("message", messageSource.getMessage("Size.signUpRequestDto.nickname", null, null));}}
			// 비밀번호 시작 -----------------
			// 비밀번호 빈문자열
			, new HashMap<String, Object>(){{put("name", "testName"); 
			  								 put("password", ""); 
			  								 put("email", "testRegNo@email.com"); 
			  								 put("nickname", "testnickname"); 
			  								 put("phone", "1012345678"); 
			  								 put("message", messageSource.getMessage("NotBlank.signUpRequestDto.password", null, null));}}
			// 비밀번호 포함 문자 미충족
			, new HashMap<String, Object>(){{put("name", "testName"); 
			  								 put("password", "12345678901Aa"); 
			  								 put("email", "testRegNo@email.com"); 
			  								 put("nickname", "testnickname"); 
			  								 put("phone", "1012345678"); 
			  								 put("message", messageSource.getMessage("Pattern.signUpRequestDto.password", null, null));}}
			// 비밀번호 길이 확인(10 - 50)
			, new HashMap<String, Object>(){{put("name", "testName"); 
			  								 put("password", "a1aA345*"); 
			  								 put("email", "testRegNo@email.com"); 
			  								 put("nickname", "testnickname"); 
			  								 put("phone", "1012345678"); 
			  								 put("message", messageSource.getMessage("Size.signUpRequestDto.password", null, null));}}
			// 전화번호 시작 -----------------
			// 전화번호 미포함
			, new HashMap<String, Object>(){{put("name", "testName"); 
			  								 put("password", "12345678901Aa!"); 
			  								 put("email", "testRegNo@email.com"); 
			  								 put("nickname", "testnickname"); 
			  								 put("phone", ""); 
			  								 put("message", messageSource.getMessage("NotBlank.signUpRequestDto.phone", null, null));}}
			// 전화번호 길이 확인
			, new HashMap<String, Object>(){{put("name", "testName"); 
			  								 put("password", "12345678901Aa!"); 
			  								 put("email", "testRegNo@email.com"); 
			  								 put("nickname", "testnickname"); 
			  								 put("phone", "0123456789012345678902123456"); 
			  								 put("message", messageSource.getMessage("Size.signUpRequestDto.phone", null, null));}}
			// 이메일 시작 -----------------
			// 이메일 빈문자열
			, new HashMap<String, Object>(){{put("name", "testName"); 
			  								 put("password", "12345678901Aa!"); 
			  								 put("email", ""); 
			  								 put("nickname", "testnickname"); 
			  								 put("phone", "1012345678"); 
			  								 put("message", messageSource.getMessage("NotBlank.signUpRequestDto.email", null, null));}}
			// 이메일 형식 확인
			, new HashMap<String, Object>(){{put("name", "testName"); 
			  								 put("password", "12345678901Aa!"); 
			  								 put("email", "testReg"); 
			  								 put("nickname", "testnickname"); 
			  								 put("phone", "1012345678"); 
			  								 put("message", messageSource.getMessage("Email.signUpRequestDto.email", null, null));}}
			// 이메일 길이 확인
			, new HashMap<String, Object>(){{put("name", "testName"); 
			  								 put("password", "12345678901Aa!*"); 
			  								 put("email", "testRegtestRegNotestRegNotestRegNotestRegNotestRegNotestRegNotestRegNotestRegNotestRegNotestRegNoNo@email.com"); 
			  								 put("nickname", "testnickname"); 
			  								 put("phone", "1012345678"); 
			  								 put("message", messageSource.getMessage("Size.signUpRequestDto.email", null, null));}}
		);
		
		for (Map<String, Object> signupParam : signupParams) {

			// given
	    	SignUpRequestDto user = new SignUpRequestDto();
	    	user.setName((String)signupParam.get("name"));
	    	user.setEmail((String)signupParam.get("email"));
	    	user.setNickname((String)signupParam.get("nickname"));
	    	user.setPassword((String)signupParam.get("password"));
	    	user.setPhone((String)signupParam.get("phone"));
	    	user.setType("user");
	    	
	        String json = new ObjectMapper().writeValueAsString(user);

	        //when
	        ResultActions resultActions = mockMvc.perform(post("/account/signup")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(json))
	                .andDo(print());

	        //then
	        resultActions
	                 .andExpect(status().isOk())
	                 .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
	                 .andExpect(MockMvcResultMatchers.jsonPath("$.status", is("fail")))
	                 .andDo(print());
		}
	}

    @Test
    @Order(3)
    @DisplayName("로그인 성공")
    public void loginSuccess() throws Exception {      

        // given    
        LogInRequestDto user = new LogInRequestDto();
        user.setEmail("testRegNo@email.com");
        user.setPassword("testPAss");

        String json = new ObjectMapper().writeValueAsString(user);

        //when
        
        // 회원가입 대상
        when(service.login(any())).thenReturn(SuccessData.getSuccessData(null));
        
        ResultActions resultActions = mockMvc.perform(post("/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print());

        //then
        resultActions
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                 .andExpect(MockMvcResultMatchers.jsonPath("$.status", is("success")))
                 .andDo(print());
    }

    @Test
    @Order(4)
    @DisplayName("로그인 실패(파라미터 누락)")
    public void loginFail() throws Exception {      

        @SuppressWarnings("serial")
        List<Map<String, Object>> loginParams = List.of(
            // 이메일 누락
              new HashMap<String, Object>(){{put("email", ""); 
                                             put("password", "12345678901Aa!");}}
            // 비밀번호 누락
            , new HashMap<String, Object>(){{put("email", "testRegNo@email.com"); 
                                             put("password", "");}}
        );
        
        for (Map<String, Object> loginParam : loginParams) {

            // given
            LogInRequestDto user = new LogInRequestDto();
            user.setEmail((String)loginParam.get("email"));
            user.setPassword((String)loginParam.get("password"));
            
            String json = new ObjectMapper().writeValueAsString(user);

            //when
            ResultActions resultActions = mockMvc.perform(post("/account/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andDo(print());

            //then
            resultActions
                     .andExpect(status().isOk())
                     .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                     .andExpect(MockMvcResultMatchers.jsonPath("$.status", is("fail")))
                     .andDo(print());
        }
    }
}
