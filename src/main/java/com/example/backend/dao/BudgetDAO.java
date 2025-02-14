package com.example.backend.dao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BudgetDAO {
    String id;
    double amount;  // giơới hạn danh mục
    double spent; // đã chi tiêu
    String status;
    String category;
}
