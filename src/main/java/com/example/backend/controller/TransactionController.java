package com.example.backend.controller;

import com.example.backend.dao.TransactionDAO;
import com.example.backend.service.transaction.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class TransactionController {

    TransactionService transactionService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getTransactionByUser(@PathVariable String userId){
        return ResponseEntity.ok(transactionService.getTransactionByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable String id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> saveTransaction(@PathVariable String userId , @RequestBody TransactionDAO transactionDAO){
         transactionService.saveTransaction(transactionDAO, userId);
         return ResponseEntity.ok("Lưu thành công");
    }

    @GetMapping("/four/{userId}")
    public ResponseEntity<?> getFourTransaction(@PathVariable String userId){
        return ResponseEntity.ok(transactionService.getFourTransaction(userId));
    }
}
