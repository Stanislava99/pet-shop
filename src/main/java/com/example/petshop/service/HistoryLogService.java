package com.example.petshop.service;

import com.example.petshop.entity.HistoryLog;
import com.example.petshop.repo.HistoryLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class HistoryLogService {
    @Autowired
    private HistoryLogRepository historyLogRepository;

    public HistoryLog createHistoryLog(int successfullyBoughtPet, int unsuccessfullyBoughtPet) {
        HistoryLog historyLog = new HistoryLog();
        historyLog.setExecutionDate(LocalDateTime.now());
        historyLog.setSuccessfullyBoughtPets(successfullyBoughtPet);
        historyLog.setUnsuccessfullyBoughtPet(unsuccessfullyBoughtPet);
        return historyLogRepository.save(historyLog);
    }

    public List<HistoryLog> getAllHistoryLogs() {
        return historyLogRepository.findAll();
    }

    public String printHistoryLog() {
        List<HistoryLog> logs = getAllHistoryLogs();
        StringBuilder logHistory = new StringBuilder();

        logHistory.append(String.format("%10s | %20s | %20s%n", "DATE", "SUCCESSFUL PURCHASES", "UNSUCCESSFUL PURCHASES"));
        for (HistoryLog log : logs) {
            LocalDateTime dateTime = LocalDateTime.parse(log.getExecutionDate().toString());
            String dateString = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            logHistory.append(String.format("%10s | %20d | %20d%n", dateString, log.getSuccessfullyBoughtPets(), log.getUnsuccessfullyBoughtPet()));
        }
        return logHistory.toString();
    }
}