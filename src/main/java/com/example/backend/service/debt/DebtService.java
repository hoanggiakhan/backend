package com.example.backend.service.debt;

import com.example.backend.dao.request.DebtDAO;
import com.example.backend.entity.Debt;
import com.example.backend.entity.DebtStatus;
import com.example.backend.entity.DebtType;
import com.example.backend.entity.User;
import com.example.backend.repository.DebtRepository;
import com.example.backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class DebtService {
    DebtRepository debtRepository;
    UserRepository userRepository;
    @CacheEvict(value = "debts", allEntries = true)
    public void createDebt(DebtDAO request , String userId){
        User user = userRepository.findById(userId).orElseThrow();
        Debt debt = new Debt();
        debt.setUser(user);
        debt.setBorrower(request.getBorrower());
        debt.setAmount(request.getAmount());
        debt.setDueDate(request.getDueDate());
        debt.setDebtType(DebtType.valueOf(request.getDebtType().toLowerCase()));
        debt.setDebtStatus(DebtStatus.valueOf(request.getDebtStatus().toLowerCase()));
        debtRepository.save(debt);
    }
    @Cacheable("debts")
    public List<DebtDAO> getDebtByUser(String userId){
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Không tìm thấy người dùng với ID: " + userId);
        }

        return debtRepository.findByUserId(userId).stream()
                .map(debt -> new DebtDAO(debt.getId(),debt.getAmount(),debt.getDebtType().name(),debt.getBorrower(),debt.getDueDate(),debt.getDebtStatus().name()))
                .collect(Collectors.toList());
    }
    @CacheEvict(value = "debts", allEntries = true)
    public void deleteDebt(String debtId){
        debtRepository.deleteById(debtId);
    }

    @CacheEvict(value = "debts", allEntries = true)
    public void updateDebt(DebtDAO debtDAO , String debtId){
        Debt debt = debtRepository.findById(debtId).orElseThrow();
        debt.setAmount(debtDAO.getAmount());
        debtRepository.saveAndFlush(debt);
    }
}
