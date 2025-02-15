package com.example.backend.service.user;

import com.example.backend.dao.ReportDAO;
import com.example.backend.dao.TransactionDAO;
import com.example.backend.dao.request.UserRequest;
import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.report.ReportService;
import com.example.backend.service.transaction.TransactionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    TransactionService transactionService;
    ReportService reportService;
    public void createUser(UserRequest userRequest){
        User user = new User();
        user = UserMapper.mapperUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userRepository.save(user);
    }

    public UserRequest getInfomation(String userId){
        User user = userRepository.findById(userId).orElse(null);

        UserRequest request = new UserRequest();
        request.setId(user.getId());
        request.setEmail(user.getEmail());
        request.setFullName(user.getFullName());
        request.setPassword(user.getPassword());
        request.setUsername(user.getUsername());
        return request;
    }

    public ResponseEntity<?> getDashboardData(@PathVariable String userId) {
    ReportDAO reportDAO = reportService.generateReportByMonth1(userId);
    double totalIncome = reportDAO.getIncome();
    double totalExpenses = reportDAO.getExpense();
    double savings = totalIncome - totalExpenses;
    List<ReportDAO> expenseTrend = reportService.getReportByUser(userId);
    List<TransactionDAO> recentTransactions = transactionService.getFourTransaction(userId);

    Map<String, Object> response = new HashMap<>();
    response.put("totalIncome", totalIncome);
    response.put("totalExpenses", totalExpenses);
    response.put("savings", savings);
    response.put("expenseTrend", expenseTrend);
    response.put("recentTransactions", recentTransactions);

    return ResponseEntity.ok(response);
}

}
