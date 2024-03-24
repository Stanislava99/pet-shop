package com.example.petshop.controller;

import com.example.petshop.service.HistoryLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class HistoryLogController {
    @Autowired
    private HistoryLogService historyLogService;

    @GetMapping("/history")
    public String printHistoryLog() {
        return historyLogService.printHistoryLog();
    }
}
