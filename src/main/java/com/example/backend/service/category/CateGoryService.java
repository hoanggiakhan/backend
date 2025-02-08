package com.example.backend.service.category;

import com.example.backend.dao.request.CategoryRequest;
import com.example.backend.dao.response.CategoryResponse;
import com.example.backend.entity.Category;
import com.example.backend.entity.CategoryType;
import com.example.backend.entity.User;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CateGoryService {
    CategoryRepository categoryRepository;
    UserRepository userRepository;

    public void createCategory(CategoryRequest request, String userId) {
        if (request == null || request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên danh mục không được để trống.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID: " + userId));

        Category category = new Category();
        category.setCategoryType(CategoryType.valueOf(request.getType().toLowerCase()));
        category.setUser(user);
        category.setName(request.getName().trim());

        categoryRepository.save(category);
    }

    public List<CategoryResponse> getCategoryByUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Không tìm thấy người dùng với ID: " + userId);
        }

        return categoryRepository.findByUserId(userId).stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName(),
                        category.getCategoryType().name()))
                .collect(Collectors.toList());
    }

    public void deleteCategoryById(String categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public void updateCategory(CategoryRequest request, String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + id));
        category.setName(request.getName());
        String type = request.getType().trim().toLowerCase();
        switch (type) {
            case "income" -> category.setCategoryType(CategoryType.income);
            case "expense" -> category.setCategoryType(CategoryType.expense);
            default -> throw new IllegalArgumentException("Loại danh mục không hợp lệ: " + request.getType());
        }

        categoryRepository.save(category);
    }

}
