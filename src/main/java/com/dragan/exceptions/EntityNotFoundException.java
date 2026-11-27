package com.dragan.exceptions;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityname, UUID uuid) {
        super(entityname + " with id " + uuid + " not found");
    }
}
