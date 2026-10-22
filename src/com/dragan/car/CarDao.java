package com.dragan.car;

import java.util.List;
import java.util.UUID;

public interface CarDao {

    List<Car> findAll();

    Car findById(UUID carUUID);

}
