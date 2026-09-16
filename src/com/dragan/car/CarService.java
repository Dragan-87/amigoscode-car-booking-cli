package com.dragan.car;

import java.util.UUID;

public class CarService {
    private final CarDao carDao = new CarDao();

    public Car[] findAllCars() {
        return carDao.findAll();
    }

    public Car findCarById(UUID uuid) {
        return carDao.findById(uuid);
    }
}
