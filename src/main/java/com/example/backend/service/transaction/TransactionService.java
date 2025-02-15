package com.example.backend.service.transaction;

import com.example.backend.dao.TransactionDAO;
import com.example.backend.entity.Budget;
import com.example.backend.entity.Category;
import com.example.backend.entity.Transaction;
import com.example.backend.entity.User;
import com.example.backend.repository.BudgetRepository;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.TransactionRepository;
import com.example.backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class TransactionService {
    TransactionRepository transactionRepository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;
    BudgetRepository budgetRepository;
    public List<TransactionDAO> getTransactionByUser(String userId){
        User user = userRepository.findById(userId).orElseThrow();
        return user.getTransactions().stream().map(
                transaction -> new TransactionDAO(transaction.getId()
                        , transaction.getAmount()
                        , transaction.getNote()
                        , transaction.getDate()
                        , transaction.getCategory() != null ? transaction.getCategory().getName() : " "
                        , transaction.getType())
        ).collect(Collectors.toList());
    }

    public void deleteTransaction(String id){
        transactionRepository.deleteById(id);
    }

    public void saveTransaction(TransactionDAO transactionDAO , String userId){
        User user = userRepository.findById(userId).orElseThrow();
        Category category = categoryRepository.findByName(transactionDAO.getCategory());
        Budget budget = budgetRepository.findByCategory(category);
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDAO.getAmount());
        transaction.setCategory(category);
        transaction.setDate(LocalDateTime.now());
        transaction.setNote(transactionDAO.getNotes());
        transaction.setType(transactionDAO.getType());
        transaction.setUser(user);
      
        transactionRepository.save(transaction);
        if(transactionDAO.getType().equals("expense")){
            budget.setSpent(budget.getSpent()+transactionDAO.getAmount());
            budgetRepository.saveAndFlush(budget);
        }
    }

    public List<TransactionDAO> getFourTransaction(String userId){
        return transactionRepository.findLatestTransactions(userId).stream().map(transaction -> 
           new TransactionDAO(transaction.getId(),
            transaction.getAmount(),
            transaction.getNote(), 
            transaction.getDate() ,
            transaction.getCategory() != null ? transaction.getCategory().getName() : " " ,
            transaction.getType())
        ).collect(Collectors.toList());
    }
}
