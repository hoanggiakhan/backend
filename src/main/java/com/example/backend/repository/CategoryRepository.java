package com.example.backend.repository;

import com.example.backend.entity.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category , String> {
    List<Category> findByUserId(String userId);
}
