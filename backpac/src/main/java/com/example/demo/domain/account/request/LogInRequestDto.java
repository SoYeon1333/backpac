package com.example.demo.domain.account.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class LogInRequestDto {
	
	@NotBlank
	@NotEmpty
	private String email;
	private String password;

}
