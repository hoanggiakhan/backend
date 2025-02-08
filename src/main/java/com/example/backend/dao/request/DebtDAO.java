package com.example.backend.dao.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebtDAO {
    String id;
    double amount;
    String debtType;
    String borrower;  // tên người vay mượn
    LocalDateTime dueDate; // hạn
    String debtStatus;
}
