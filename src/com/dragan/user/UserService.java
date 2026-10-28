package com.dragan.user;

import java.util.UUID;

public class UserService {
    private UserDao userDao = new UserDao() ;

    public User[] findAll() {
        return userDao.findAll();
    }

    public User findByID(UUID uuid) {
        return userDao.findById(uuid);
    }
}
