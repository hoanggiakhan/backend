package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Debt {
    // vay nợ
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    double amount;
    @Enumerated(EnumType.STRING)
    DebtType debtType;
    String borrower;  // tên người vay mượn
    LocalDateTime dueDate; // hạn
    @Enumerated(EnumType.STRING)
    DebtStatus debtStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
