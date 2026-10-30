package com.dragan.car;

import java.util.List;
import java.util.UUID;

public class CarService {
    private final CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public List<Car> findAllCars() {
        return carDao.findAll();
    }

    public Car findCarById(UUID uuid) {
        return carDao.findById(uuid);
    }

}
