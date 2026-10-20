package com.dragan.car;

import java.util.UUID;

public interface CarDao {

    Car[] findAll();

    Car findById(UUID carUUID);

}
