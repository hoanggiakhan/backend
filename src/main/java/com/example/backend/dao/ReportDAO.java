package com.example.backend.dao;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportDAO {
    int month;
    int year;
    double income;  //tổng thu nhập
    double expense; //tổng chi tiêu
    double saving; // tiền tết kiệm
}
