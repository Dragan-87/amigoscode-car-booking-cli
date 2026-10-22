package com.dragan.car;

import java.util.List;
import java.util.UUID;

public class CarService {
    private final CarArrayDataAccessService carDao = new CarArrayDataAccessService();

    public List<Car> findAllCars() {
        return carDao.findAll();
    }

    public Car findCarById(UUID uuid) {
        return carDao.findById(uuid);
    }

}
