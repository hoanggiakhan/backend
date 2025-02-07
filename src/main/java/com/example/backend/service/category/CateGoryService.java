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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class CateGoryService {
    CategoryRepository categoryRepository;
    UserRepository userRepository;
    public void createCategory(CategoryRequest request , String userId){
        Category category = new Category();
        if(request.getType().equals("expense")){
            category.setCategoryType(CategoryType.expense);
        }else {
            category.setCategoryType(CategoryType.income);
        }
        User user = userRepository.findById(userId).orElseThrow();
        category.setUser(user);
        category.setName(request.getName());
        categoryRepository.save(category);
    }

    public List<CategoryResponse> getCategoryByUser(String userId){
        User user = userRepository.findById(userId).orElseThrow();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for(Category category : user.getCategories()){
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(category.getId());
            categoryResponse.setName(category.getName());
            categoryResponse.setType(category.getCategoryType().name());
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;
    }

    public void deleteCategoryById(String categoryId){
        categoryRepository.deleteById(categoryId);
    }

    public void updateCategory(CategoryRequest request , String id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + id));
        category.setName(request.getName());
        if(request.getType().equals("income")){
            category.setCategoryType(CategoryType.income);
        }else {
            category.setCategoryType(CategoryType.expense);
        }
        categoryRepository.saveAndFlush(category);
    }
}
