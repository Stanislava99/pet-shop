package com.example.petshop.controller;

import com.example.petshop.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buy")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping()
    public void buyPets() {
        purchaseService.buyPets();
    }

    @GetMapping("/history")
    public String printHistoryLog() {
        return purchaseService.printHistoryLog();
    }
}
