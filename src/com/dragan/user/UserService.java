package com.dragan.user;

import java.util.UUID;

public class UserService {
    private UserArrayDataAccessService UserArrayDataAccessService = new UserArrayDataAccessService() ;

    public User[] findAll() {
        return UserArrayDataAccessService.findAll();
    }

    public User findByID(UUID uuid) {
        return UserArrayDataAccessService.findByID(uuid);
    }
}
