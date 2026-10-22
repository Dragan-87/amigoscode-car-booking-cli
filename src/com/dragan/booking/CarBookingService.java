package com.dragan.booking;
import com.dragan.car.Car;
import com.dragan.car.CarService;
import com.dragan.user.User;
import com.dragan.user.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class CarBookingService {
    private final CarBookingDao carBookingDao;
    private final CarService carService;
    private final UserService userService;

    public CarBookingService(CarBookingDao carBookingDao, CarService carService, UserService userService) {
        this.carBookingDao = carBookingDao;
        this.carService = carService;
        this.userService = userService;

    }

    public CarBooking[] findAllBookingsByUserId(UUID uuid) {
        return carBookingDao.findAllBookingsByUserId(uuid);
    }

    public CarBooking[] findAll() {
        return carBookingDao.findAll();
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

    public Car[] getAvailableCars(Predicate<Car> filter) {
        Car[] cars = carService.findAllCars();
        int counter = 0;
        for (Car car : cars) {
            if (!isCarBooked(car) && filter.test(car)) {
                counter++;
            }
        }

        Car[] availableCars = new Car[counter];
        int availableCarsIndex = 0;

        for (Car car : cars) {
            if (!isCarBooked(car) && filter.test(car)) {
                availableCars[availableCarsIndex] = car;
                availableCarsIndex++;
            }
        }
        return availableCars;
    }

    public void save(CarBooking booking) {
        carBookingDao.save(booking);
    }

    public void bookCar(UUID userUUID, UUID carUUID) {
        Car car = carService.findCarById(carUUID);
        User user = userService.findByID(userUUID);
        CarBooking booking = new CarBooking(
                UUID.randomUUID(),
                user,
                car,
                LocalDate.now(),
                LocalDate.now(),
                car.getRentalPricePerDay(),
                LocalDateTime.now()
        );
        save(booking);
    }

    public void deleteById(UUID uuid) {
        carBookingDao.deleteByID(uuid);
    }
}
