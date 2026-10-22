package com.dragan.user;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserArrayDataAccessService userArrayDataAccessService = new UserArrayDataAccessService() ;

    public List<User> findAll() {
        return userArrayDataAccessService.findAll();
    }

    public User findByID(UUID uuid) {
        return userArrayDataAccessService.findByID(uuid);
    }
}
