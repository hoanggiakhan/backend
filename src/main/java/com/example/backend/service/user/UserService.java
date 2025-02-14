package com.example.backend.service.user;

import com.example.backend.dao.request.UserRequest;
import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
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

}
