package com.example.backend.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dao.BudgetDAO;
import com.example.backend.service.budget.BudgetService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class BudgetController {
    BudgetService budgetService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllBudgetByUser(@PathVariable String userId){
        return ResponseEntity.ok(budgetService.getAllBudgetByUser(userId));
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createBudget(@PathVariable String userId , @RequestBody BudgetDAO budgetDAO){
        budgetService.createBudget(budgetDAO, userId);
        return ResponseEntity.ok("Đã thêm thành công");
    }
}
