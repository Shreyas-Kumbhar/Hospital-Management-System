package com.hospital.hospitalMngtSys.service.impl;

import com.hospital.hospitalMngtSys.entity.User;
import com.hospital.hospitalMngtSys.repository.UserRepository;
import com.hospital.hospitalMngtSys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(String username, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            return null; // username taken
        }
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .role("ROLE_ADMIN")
                .build();
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
