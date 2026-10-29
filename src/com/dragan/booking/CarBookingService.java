package com.dragan.booking;
import com.dragan.car.Car;
import com.dragan.car.CarService;
import com.dragan.user.User;
import com.dragan.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    public void bookCar(UUID userUUID, UUID carUUID, LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date must not be in the past");
        }
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        Car car = carService.findCarById(carUUID);
        User user = userService.findByID(userUUID);
        CarBooking booking = new CarBooking(
                UUID.randomUUID(),
                user,
                car,
                startDate,
                endDate,
                calculatePrice(car, startDate, endDate),
                LocalDateTime.now()
        );
        save(booking);
    }

    private BigDecimal calculatePrice(Car car, LocalDate startDate, LocalDate endDate) {
        long rentalDays = ChronoUnit.DAYS.between(startDate, endDate);
        return car.getRentalPricePerDay().multiply(BigDecimal.valueOf(rentalDays));
    }

    public void deleteById(UUID uuid) {
        carBookingDao.deleteByID(uuid);
    }
}
