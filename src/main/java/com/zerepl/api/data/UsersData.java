package com.zerepl.api.data;

import com.zerepl.api.entity.User;
import com.zerepl.api.repository.UserRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UsersData implements UserRepository{
        private final Map USERS_STORE = new ConcurrentHashMap();

        @Override
        public String create(User newUser) {
            String id = UUID.randomUUID().toString();
            User user = new User();

            user.setId(id);
            user.setLogin(newUser.getLogin());
            user.setPassword(newUser.getPassword());

            USERS_STORE.put(id, user);
            return id;
        }

    @Override
    public User getUser(String id) {
        User user = new User();

        Object requiredUser = USERS_STORE.get(id);
        user = User.class.cast(requiredUser);
        return user;
    }



}
