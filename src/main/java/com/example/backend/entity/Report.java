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
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    int month;
    int year;
    double totalIncome;  //tổng thu nhập
    double totalExpense; //tổng chi tiêu
    double savings; // tiền tết kiệm

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
