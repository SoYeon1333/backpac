package com.example.demo.domain.account.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUpRequestDto {

	@Pattern(regexp = "[a-zA-Zㄱ-힣]*$")
	@NotBlank
	@NotEmpty
	@Size(max=20)
	private String name;

	@Pattern(regexp = "[a-z]*$")
	@NotBlank
	@NotEmpty
	@Size(max=30)
	private String nickname;

	@Size(min=10, max=50)
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+]).*$")
	@NotBlank
	@NotEmpty
	private String password;

	@Pattern(regexp = "[0-9]*$")
	@NotBlank
	@NotEmpty
	@Size(max=20)
	private String phone;

	@Email
	@NotBlank
	@NotEmpty
	@Size(max=100)
	private String email;
	
	private String sex;
	private String type;
	
}
