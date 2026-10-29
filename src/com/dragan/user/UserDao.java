package com.dragan.user;

import java.util.List;
import java.util.UUID;

public interface UserDao {

    List<User> findAll();

    User findByID(UUID userUUID);

}
