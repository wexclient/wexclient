package com.livngroup.gds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.livngroup.gds.domain.LivnRequestResponseLog;

public interface LivnRequestResponseLogRepository extends JpaRepository<LivnRequestResponseLog, String> {
}