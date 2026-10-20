package com.dragan.car;

import java.util.UUID;

public class CarService {
    private final CarArrayDataAccessService carDao = new CarArrayDataAccessService();

    public Car[] findAllCars() {
        return carDao.findAll();
    }

    public Car findCarById(UUID uuid) {
        return carDao.findById(uuid);
    }

}
