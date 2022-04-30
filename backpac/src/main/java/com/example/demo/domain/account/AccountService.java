package com.example.demo.domain.account;

import org.springframework.stereotype.Service;

import com.example.demo.domain.account.request.LogInRequestDto;
import com.example.demo.domain.account.request.SignUpRequestDto;
import com.example.demo.global.dto.response.BackpacResponseData;
import com.example.demo.global.dto.response.ErrorData;
import com.example.demo.global.dto.response.SuccessData;

@Service
public class AccountService {

	private AccountRepository repository;
	
	public AccountService(AccountRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * 회원 가입
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BackpacResponseData signup(SignUpRequestDto param) throws Exception {

		AccountEntity account = new AccountEntity();
		
		account.setName(param.getName());
		account.setNickname(param.getNickname());
		account.setPassword(param.getPassword());
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

		if (account.getPassword().equals(param.getPassword())) {
			return SuccessData.getSuccessData("로그인 성공");
		}
		else {
			return ErrorData.getErrorData("로그인 실패");
		}
	}
	
}
