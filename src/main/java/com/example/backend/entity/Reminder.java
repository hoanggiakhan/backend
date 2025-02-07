package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reminder {
    // Nhắc nhở
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String message;
    LocalDate remindDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
