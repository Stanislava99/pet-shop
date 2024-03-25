package com.example.petshop.repo;

import com.example.petshop.entity.HistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<HistoryLog, Long> {
}