package com.dragan.user;

import java.util.UUID;

public interface UserDao {

    User[] findAll();

    User findByID(UUID userUUID);

}
