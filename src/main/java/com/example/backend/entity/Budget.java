package com.example.backend.entity;



import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Budget {
    // Ngân sách
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    double amount;  // giơới hạn danh mục
    double spent; // đã chi tiêu
    int month; // tháng áp dụng
    int year; // năm áp dụng

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne
    @JoinColumn(name = "category_id")
    Category category;
}
