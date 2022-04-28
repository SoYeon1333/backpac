package com.example.demo.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
	
	public AccountEntity findByNickname(String nickname);
	
}