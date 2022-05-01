package com.example.demo.domain.account;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class AccountEntity {
	
	@Id
	private long id;
	private String name;
	private String nickname;
	private String password;
	private String phone;
	private String email;
	private String sex;
	private String type;
	
}
