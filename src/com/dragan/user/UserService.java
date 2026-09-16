package com.dragan.user;

public class UserService {
    private UserDao userDao = new UserDao() ;

    public User[] findAll() {
        return userDao.findAll();
    }
}
