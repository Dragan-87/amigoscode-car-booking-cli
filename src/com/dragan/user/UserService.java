package com.dragan.user;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User[] getAllUsers() {
        return userDao.getAllUser();
    }
}
