package com.example.backend.repository;

import com.example.backend.entity.Budget;
import com.example.backend.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget , String> {
    boolean existsByCategory(Category category);
    Budget findByCategory(Category category);
}
