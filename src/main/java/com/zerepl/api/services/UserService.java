package com.zerepl.api.services;

import com.zerepl.api.entity.User;
import com.zerepl.api.repository.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createUser(User user) {
        return userRepository.createUser(user);
    }

    public User getUser(String id) {
        return userRepository.getUser(id);
    }

    public void deleteUser(String id) {
        userRepository.deleteUser(id);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }
}
