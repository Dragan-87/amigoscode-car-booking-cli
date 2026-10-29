package com.dragan.user;

import com.dragan.exceptions.EntityNotFoundException;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

public class UserFileDataAccessService implements UserDao {

    private static final User[] DEFAULT_USERS = {
            new User(UUID.fromString("d6342251-554c-4ae5-a31c-1174f41ee424"), "Max"),
            new User(UUID.fromString("7bc3e37c-f158-4153-892e-3e7a6bc5b627"), "Peter"),
    };

    private final String filePath;

    public UserFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    private User[] readAll() {
        File file = new File(filePath);
        if (!file.exists()) {
            writeAll(DEFAULT_USERS);
            return DEFAULT_USERS;
        }
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (User[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Can't read users: " + filePath, e);
        }
    }

    private void writeAll(User[] users) {
        try (
                FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(users);
        } catch (IOException e) {
            throw new RuntimeException("Can't write in file " + filePath, e);
        }
    }

    @Override
    public User[] findAll() {
        return readAll();
    }

    @Override
    public User findByID(UUID userUUID) {
        for (User user : readAll()) {
            if (Objects.equals(userUUID, user.getId())) {
                return user;
            }
        }
        throw new EntityNotFoundException("User", userUUID);
    }
}
