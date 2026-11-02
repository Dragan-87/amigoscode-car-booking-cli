package com.dragan.user;

import com.dragan.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class UserArrayDataAccessService implements UserDao {
    private static final List<User> users = new ArrayList<>(
            List.of(
                    new User(UUID.fromString("d6342251-554c-4ae5-a31c-1174f41ee424"), "Max"),
                    new User(UUID.fromString("7bc3e37c-f158-4153-892e-3e7a6bc5b627"), "Peter")
            )
    );

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public User findByID(UUID userUUID) throws EntityNotFoundException {
        return users.stream().filter(user -> Objects.equals(userUUID, user.getId())).findFirst().orElseThrow(() -> new EntityNotFoundException("User", userUUID));
    }
}
