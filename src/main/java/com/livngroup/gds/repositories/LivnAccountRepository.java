package com.livngroup.gds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.livngroup.gds.domain.LivnAccount;

public interface LivnAccountRepository extends JpaRepository<LivnAccount, String> {
	 
	public LivnAccount findByUsername(String username);

}
