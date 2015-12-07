package com.livngroup.gds.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.livngroup.gds.domain.BackupCard;

public interface BackupCardRepository extends JpaRepository<BackupCard, String> {

	  List<BackupCard> findByCardNumber(String cardNumber);

}