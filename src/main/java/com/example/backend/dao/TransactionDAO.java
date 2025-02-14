package com.example.backend.dao;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDAO {
    String id;
    double amount;
    String notes;
    LocalDateTime date;
    String category;
    String type;
}
