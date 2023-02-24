package com.example.currencyexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.currencyexchange.models.AuditInfo;

@Repository
public interface AuditInfoRepository extends JpaRepository<AuditInfo, Integer>{
        
} 