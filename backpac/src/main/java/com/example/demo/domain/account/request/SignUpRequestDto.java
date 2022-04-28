package com.example.demo.domain.account.request;

import lombok.Data;

@Data
public class SignUpRequestDto {
	
	private String name;
	private String nickname;
	private String password;
	private int phone;
	private String email;
	private String sex;
	private String type;
	
}
