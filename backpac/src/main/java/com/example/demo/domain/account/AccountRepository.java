package com.example.demo.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    public AccountEntity findByEmail(String email);
	public AccountEntity findTop1ByEmailOrNickname(String email, String nickname);
	
}