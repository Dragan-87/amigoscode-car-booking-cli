package com.dragan.booking;
import com.dragan.car.Car;
import com.dragan.car.CarService;

import java.util.Objects;
import java.util.UUID;

public class CarBookingService {
    private final CarBookingDao carBookingDao = new CarBookingDao();
    private final CarService carService = new CarService();

    public CarBooking[] findAllBookingsByUserId(UUID uuid) {
        return carBookingDao.findAllBookingsByUserId(uuid);
    }

    public CarBooking[] findAll() {
        return carBookingDao.findAll();
    }

    public CarBooking[] findAllByUserID(UUID uuid) {
        return carBookingDao.findAllBookingsByUserId(uuid);
    }

    public boolean isCarBooked(Car car) {
        CarBooking[] carBookings = findAll();
        for (CarBooking carBooking : carBookings) {
            if (carBooking != null && Objects.equals(car.getId(), carBooking.getCar().getId())) {
                return true;
            }
        }
        return false;
    }

    public Car[] getAvailableCars() {
        Car[] cars = carService.findAllCars();
        int counter = 0;
        for (Car car : cars) {
            if (!isCarBooked(car)) {
                counter++;
            }
        }
        Car[] availableCars = new Car[counter];
        int availableCarsIndex = 0;
        for (Car car : cars) {
            if (!isCarBooked(car)) {
                availableCars[availableCarsIndex] = car;
                availableCarsIndex++;
            }
        }
        return availableCars;
    }
}
