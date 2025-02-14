package com.example.backend.service.report;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dao.ReportDAO;
import com.example.backend.entity.Report;
import com.example.backend.entity.Transaction;
import com.example.backend.entity.User;
import com.example.backend.repository.ReportRepository;
import com.example.backend.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class ReportService {
    ReportRepository reportRepository;
    UserRepository userRepository;
    // public List<ReportDAO> getAllReportByUser(String userId) {
    //     User user = userRepository.findById(userId).orElse(null);
    //     if (user == null) {
    //         return Collections.emptyList(); // Trả về danh sách rỗng nếu không tìm thấy user
    //     }
    
    //     // Chuyển đổi danh sách báo cáo của user thành danh sách ReportDAO
    //     List<ReportDAO> reports = user.getReports().stream()
    //         .map(report -> new ReportDAO(
    //             report.getId(),
    //             report.getMonth(),
    //             report.getYear(),
    //             report.getTotalIncome(),
    //             report.getTotalExpense(),
    //             report.getSavings()
    //         )).collect(Collectors.toList());
    
    //     // Lấy thời gian hiện tại
    //     LocalDate currentDate = LocalDate.now();
    //     int currentYear = currentDate.getYear();
    //     int currentMonth = currentDate.getMonthValue();
    
    //     // Danh sách chứa 3 tháng gần nhất (xử lý trường hợp đầu năm)
    //     List<Integer> recentMonths = new ArrayList<>();
    //     List<Integer> recentYears = new ArrayList<>();
    
    //     for (int i = 0; i < 3; i++) {
    //         int month = currentMonth - i;
    //         int year = currentYear;
    //         if (month <= 0) { // Xử lý khi lùi sang năm trước
    //             month += 12;
    //             year -= 1;
    //         }
    //         recentMonths.add(month);
    //         recentYears.add(year);
    //     }
    
    //     // Lọc các báo cáo trong 3 tháng gần nhất
    //     return reports.stream()
    //         .filter(report -> {
    //             for (int i = 0; i < 3; i++) {
    //                 if (report.getMonth() == recentMonths.get(i) && report.getYear() == recentYears.get(i)) {
    //                     return true;
    //                 }
    //             }
    //             return false;
    //         })
    //         .collect(Collectors.toList());
    // }

      public List<ReportDAO> generateReports(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return Collections.emptyList(); // Trả về danh sách rỗng nếu không tìm thấy user
        }
        List<Transaction> transactions = user.getTransactions();
        LocalDate now = LocalDate.now();
        LocalDate threeMonthsAgo = now.minusMonths(2).withDayOfMonth(1); // Lấy ngày đầu tiên của 3 tháng trước

        // Lọc giao dịch trong 3 tháng gần nhất
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(t -> {
                    LocalDate transactionDate = t.getDate().toLocalDate();
                    return !transactionDate.isBefore(threeMonthsAgo);
                })
                .collect(Collectors.toList());

        // Nhóm theo (year, month)
        Map<String, List<Transaction>> groupedTransactions = filteredTransactions.stream()
                .collect(Collectors.groupingBy(t -> t.getDate().getYear() + "-" + t.getDate().getMonthValue()));

        // Tạo danh sách báo cáo từ các nhóm giao dịch
        List<ReportDAO> reports = new ArrayList<>();
        for (Map.Entry<String, List<Transaction>> entry : groupedTransactions.entrySet()) {
            List<Transaction> trans = entry.getValue();
            int year = trans.get(0).getDate().getYear();
            int month = trans.get(0).getDate().getMonthValue();

            double income = trans.stream()
                    .filter(t -> t.getType().equalsIgnoreCase("income"))
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            double expense = trans.stream()
                    .filter(t -> t.getType().equalsIgnoreCase("expense"))
                    .mapToDouble(Transaction::getAmount)
                    .sum();

            reports.add(new ReportDAO(month, year, income, expense , income-expense));
        }
        return reports;
    }

    public List<ReportDAO> generateReportByMonth(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return Collections.emptyList(); // Trả về danh sách rỗng nếu không tìm thấy user
        }
        List<Transaction> transactions = user.getTransactions();
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();
    
        // Lọc giao dịch trong tháng hiện tại và năm hiện tại
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(t -> {
                    LocalDate transactionDate = t.getDate().toLocalDate();
                    return transactionDate.getMonthValue() == currentMonth && transactionDate.getYear() == currentYear;
                })
                .collect(Collectors.toList());
    
        double income = filteredTransactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("income"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    
        double expense = filteredTransactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("expense"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    
        // Tạo báo cáo cho tháng hiện tại
        List<ReportDAO> reports = new ArrayList<>();
        reports.add(new ReportDAO(currentMonth, currentYear, income, expense, income - expense));
    
        return reports;
    }

    public void saveReports(ReportDAO reportDAO , String userId){
        User user = userRepository.findById(userId).orElse(null);
        LocalDate date = LocalDate.now();
       int month = date.getMonthValue();
       int year = date.getYear();
        Report report = new Report();
        report.setTotalExpense(reportDAO.getExpense());
        report.setTotalIncome(reportDAO.getIncome());
        report.setSavings(reportDAO.getSaving());
        report.setUser(user);
        report.setMonth(month);
        report.setYear(year);
        reportRepository.save(report);
    }
    
}
