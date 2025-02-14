package com.example.backend.controller;

import com.example.backend.dao.request.CategoryRequest;
import com.example.backend.service.category.CateGoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class CategoryController {
    CateGoryService cateGoryService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request , @PathVariable String userId){
        cateGoryService.createCategory(request , userId);
        return ResponseEntity.ok("Thêm thành công");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCategoryByUser(@PathVariable String userId){
        return ResponseEntity.ok(cateGoryService.getCategoryByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        cateGoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequest request , @PathVariable String id){
        cateGoryService.updateCategory(request , id);
        return ResponseEntity.ok("Sửa thành công");
    }

    @GetMapping("/budget/{userId}")
    public ResponseEntity<?> getCategory(@PathVariable String userId){
        return ResponseEntity.ok(cateGoryService.getCategory(userId));
    }
}
