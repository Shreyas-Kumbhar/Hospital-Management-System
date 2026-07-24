package com.hospital.hospitalMngtSys.service;

import com.hospital.hospitalMngtSys.entity.User;

public interface UserService {
    /**
     * Register a new user. Returns null if username already taken.
     */
    User register(String username, String rawPassword);

    boolean existsByUsername(String username);
}
