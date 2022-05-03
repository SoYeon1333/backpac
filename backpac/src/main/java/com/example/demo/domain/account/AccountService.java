package com.example.demo.domain.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.account.request.LogInRequestDto;
import com.example.demo.domain.account.request.SignUpRequestDto;
import com.example.demo.domain.account.response.MeResponseDto;
import com.example.demo.global.dto.response.BackpacResponseData;
import com.example.demo.global.dto.response.ErrorData;
import com.example.demo.global.dto.response.SuccessData;

@Service
public class AccountService {

	private AccountRepository repository;
	private PasswordEncoder passwordEncoder;
	
	public AccountService(AccountRepository repository
	                    , PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}
	
	/**
	 * 회원 가입
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BackpacResponseData signup(SignUpRequestDto param) throws Exception {

	    String email = param.getEmail();
	    String nickname = param.getNickname();
		AccountEntity account = repository.findTop1ByEmailOrNickname(email, nickname);
		
		if (account != null) {
	        if (account.getEmail().equals(email)) {
	            return ErrorData.getErrorData("이미 등록된 이메일입니다.");
	        }
	        if (account.getNickname().equals(nickname)) {
	            return ErrorData.getErrorData("이미 등록된 닉네임입니다.");
	        }
		}
		
		// 회원가입을 위한 AccountEntity
		account = new AccountEntity();
		account.setName(param.getName());
		account.setNickname(param.getNickname());
		account.setPassword(passwordEncoder.encode(param.getPassword()));
		account.setPhone(param.getPhone());
		account.setEmail(param.getEmail());
		account.setSex(param.getSex());
		account.setType(param.getType());
		
		repository.save(account);
		
		return SuccessData.getSuccessData("계정 생성 성공");
	} 

	/**
	 * 로그인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BackpacResponseData login(LogInRequestDto param) throws Exception {

		AccountEntity account = repository.findByEmail(param.getEmail());

        if (account == null) {
            return new ErrorData("존재하지 않는 아이디입니다.");
        }
        else if (!passwordEncoder.matches(param.getPassword(), account.getPassword())) {
            return ErrorData.getErrorData("비밀번호를 확인해 주세요.");
        }
        
	    return SuccessData.getSuccessData("로그인 성공");
	}
	
	public BackpacResponseData me(String email) throws Exception {
	    
	    AccountEntity account = repository.findByEmail(email);
	    
	    MeResponseDto me = new MeResponseDto();
	    me.setEmail(account.getEmail());
	    me.setName(account.getName());
	    me.setNickname(account.getNickname());
	    me.setPhone(account.getPhone());
	    me.setSex(account.getSex());
	    me.setType(account.getType());
	    
	    return me;
	}
	
}
