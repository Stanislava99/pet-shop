package com.example.petshop.repo;

import com.example.petshop.entity.HistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryLogRepository extends JpaRepository<HistoryLog, Long> {
}