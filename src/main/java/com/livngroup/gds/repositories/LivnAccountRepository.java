package com.livngroup.gds.repositories;

import org.springframework.stereotype.Service;

import com.livngroup.gds.domain.LivnAccount;

@Service("livnAccountRepository")
public class LivnAccountRepository /*extends JpaRepository<LivnAccount, String>*/ {
	 
	public LivnAccount findByUsername(String username) {
		
		LivnAccount livnAccount = new LivnAccount();
		
		livnAccount.setUsername(username);
		livnAccount.setPassword("password");
		
		return livnAccount;
	}
}
