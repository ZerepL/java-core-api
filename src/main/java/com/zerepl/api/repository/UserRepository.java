package com.zerepl.api.repository;

import com.zerepl.api.entity.User;

public interface UserRepository {
    String createUser(User user);
    User getUser(String id);
    void deleteUser(String id);
    void updateUser(User user);
}
