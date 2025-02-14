package com.example.backend.mapper;

import com.example.backend.dao.request.UserRequest;
import com.example.backend.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class UserMapper {
    public static User mapperUser(UserRequest userRequest){
        return User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .fullName(userRequest.getFullName())
                .build();
    }
}
