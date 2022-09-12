package com.zerepl.api.data;

import com.zerepl.api.entity.User;
import com.zerepl.api.repository.UserRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UsersData implements UserRepository{
        private final Map USERS_STORE = new ConcurrentHashMap();

        @Override
        public String createUser(User newUser) {
            String id = UUID.randomUUID().toString();
            User user = new User();

            user.setId(id);
            user.setName(newUser.getName());
            user.setPassword(newUser.getPassword());

            USERS_STORE.put(id, user);
            return id;
        }

    @Override
    public User getUser(String id) {
        User user = new User();

        Object requiredUser = USERS_STORE.get(id);
        user = (User) requiredUser;

        return user;
    }

    @Override
    public void deleteUser(String id) {
        USERS_STORE.remove(id);
    }

    @Override
    public void updateUser(User newUser) {
        USERS_STORE.replace(newUser.getId(), newUser);
    }



}
