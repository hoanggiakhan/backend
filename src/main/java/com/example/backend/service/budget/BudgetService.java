package com.example.backend.service.budget;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.dao.BudgetDAO;
import com.example.backend.entity.Budget;
import com.example.backend.entity.Category;
import com.example.backend.entity.User;
import com.example.backend.repository.BudgetRepository;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BudgetService {
    BudgetRepository budgetRepository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;
    public List<BudgetDAO> getAllBudgetByUser(String userId){
      User user = userRepository.findById(userId).orElse(null);
      List<BudgetDAO>  budgetDAOs = new ArrayList<>();
      for(Budget budget : user.getBudgets()){
          double phanTram = (budget.getSpent()/budget.getAmount())*100;
          BudgetDAO budgetDAO = new BudgetDAO();
          budgetDAO.setCategory(budget.getCategory().getName());
          budgetDAO.setId(budget.getId());
          budgetDAO.setAmount(budget.getAmount());
          budgetDAO.setSpent(budget.getSpent());
          if(phanTram >= 90 && phanTram <= 100){
             budgetDAO.setStatus("warning");
          }else if(phanTram < 90){
            budgetDAO.setStatus("within");
          }else{
            budgetDAO.setStatus("exceeded");
          }
          budgetDAOs.add(budgetDAO);
      }
      return budgetDAOs;
    }


    public void createBudget(BudgetDAO budgetDAO, String userId){
       User user = userRepository.findById(userId).orElse(null);
       Budget budget = new Budget();
       Category category = categoryRepository.findByName(budgetDAO.getCategory());
       if(budgetRepository.existsByCategory(category)){
          throw new RuntimeException("Danh mục đã được chia ngân sách");
       }
       budget.setAmount(budgetDAO.getAmount());
       budget.setCategory(category);
       budget.setUser(user);
       budget.setSpent(budgetDAO.getSpent());
       budgetRepository.save(budget);
    }

    
}
