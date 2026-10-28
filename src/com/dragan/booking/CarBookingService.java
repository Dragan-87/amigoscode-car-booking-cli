package com.dragan.booking;
import com.dragan.car.Car;
import com.dragan.car.CarService;
import com.dragan.user.User;
import com.dragan.user.UserService;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class CarBookingService {
    private final CarBookingDao carBookingDao = new CarBookingDao();
    private final CarService carService = new CarService();
    private final UserService userService = new UserService();

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

    public Car[] availableElectricCars() {
        Car[] cars = carService.findAllCars();
        int counter = 0;
        for (Car car : cars) {
            if (!isCarBooked(car) && car.isElectric()) {
                counter++;
            }
        }
        Car[] availableElectricCars = new Car[counter];
        int availableElectricCarsIndex = 0;
        for (Car car : cars) {
            if (!isCarBooked(car) && car.isElectric()) {
                availableElectricCars[availableElectricCarsIndex] = car;
                availableElectricCarsIndex++;
            }
        }
        return availableElectricCars;
    }

    public CarBooking save(CarBooking booking) {
        return carBookingDao.save(booking);
    }

    public CarBooking bookCar(UUID userUUID, UUID carUUID, LocalDate startDate, LocalDate endDate) {
        Car car = carService.findCarById(carUUID);
        if (isCarBooked(car)) {
            throw new IllegalStateException("Car already booked");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("End date must not be before start date");
        }
        long rentalDays = calculateRentalDays(startDate, endDate);
        BigDecimal price = car.getRentalPricePerDay().multiply(BigDecimal.valueOf(rentalDays));
        User user = userService.findByID(userUUID);
        CarBooking booking = new CarBooking(
                UUID.randomUUID(),
                user,
                car,
                startDate,
                endDate,
                price,
                LocalDateTime.now()
        );
        return save(booking);
    }

    private long calculateRentalDays(LocalDate startDate, LocalDate endDate) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return Math.max(1, daysBetween);
    }

    public void deleteById(UUID uuid) {
        carBookingDao.deleteByID(uuid);
    }
}
