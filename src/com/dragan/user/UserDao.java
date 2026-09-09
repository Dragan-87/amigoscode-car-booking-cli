package com.dragan.user;

import java.util.UUID;


public class UserDao {
    private static final User[] users;

    static {
        users = new User[]{
                new User(UUID.fromString("d6342251-554c-4ae5-a31c-1174f41ee424"), "Max"),
                new User(UUID.fromString("7bc3e37c-f158-4153-892e-3e7a6bc5b627"), "Peter"),
        };
    }

    public User[] getAllUser() {
        return users;
    }
}
