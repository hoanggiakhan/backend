package com.example.backend.controller;

import com.example.backend.dao.request.LoginRequest;
import com.example.backend.dao.request.UserRequest;
import com.example.backend.dao.response.LoginResponse;
import com.example.backend.service.user.Authenticated;
import com.example.backend.service.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class UserController {
    UserService userService;
    Authenticated authenticated;
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
        return ResponseEntity.ok("Đăng ký thành công");
    }

    @PostMapping("/auth")
    public ResponseEntity<LoginResponse> auth(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticated.auth(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> Infomation(@PathVariable String userId){
        return ResponseEntity.ok(userService.getInfomation(userId));
    }
}
