package com.example.backend.controller;

import com.example.backend.dao.request.DebtDAO;
import com.example.backend.service.debt.DebtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/debts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class DebtController {
    DebtService debtService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createDebt(@RequestBody DebtDAO request , @PathVariable String userId){
        debtService.createDebt(request,userId);
        return ResponseEntity.ok("Thêm thành công");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getDebtByUser(@PathVariable String userId){
        return ResponseEntity.ok(debtService.getDebtByUser(userId));
    }

    @DeleteMapping("/{debtId}")
    public ResponseEntity<?> deleteDebt(@PathVariable String debtId){
        debtService.deleteDebt(debtId);
        return ResponseEntity.ok("Xóa thành công");
    }

    @PutMapping("/{debtId}")
    public ResponseEntity<?> updateDebt(@RequestBody DebtDAO debtDAO , @PathVariable String debtId){
        debtService.updateDebt(debtDAO , debtId);
        return ResponseEntity.ok("Sửa thành công");
    }
}
