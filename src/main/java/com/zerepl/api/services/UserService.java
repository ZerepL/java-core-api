package com.zerepl.api.services;

import com.zerepl.api.entity.User;
import com.zerepl.api.repository.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String create(User user) {
        return userRepository.create(user);
    }

    public User getUser(String id) {
        return userRepository.getUser(id);
    }

}
