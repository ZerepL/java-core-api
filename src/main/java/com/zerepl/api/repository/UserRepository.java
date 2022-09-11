package com.zerepl.api.repository;

import com.zerepl.api.entity.User;

public interface UserRepository {
    String create(User user);
    User getUser(String id);
}
