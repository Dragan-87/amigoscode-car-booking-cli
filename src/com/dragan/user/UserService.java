package com.dragan.user;

import java.util.UUID;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User[] findAll() {
        return userDao.findAll();
    }

    public User findByID(UUID uuid) {
        return userDao.findByID(uuid);
    }
}
