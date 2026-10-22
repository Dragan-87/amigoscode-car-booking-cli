package com.dragan.booking;
import com.dragan.car.Car;
import com.dragan.car.CarService;
import com.dragan.user.User;
import com.dragan.user.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public List<CarBooking> findAllBookingsByUserId(UUID uuid) {
        return carBookingDao.findAllBookingsByUserId(uuid);
    }

    public List<CarBooking> findAll() {
        return carBookingDao.findAll();
    }

    public boolean isCarBooked(Car car) {
        List<CarBooking> carBookings = findAll();
        for (CarBooking carBooking : carBookings) {
            if (carBooking != null && Objects.equals(car.getId(), carBooking.getCar().getId())) {
                return true;
            }
        }
        return false;
    }

    public List<Car> getAvailableCars(Predicate<Car> filter) {
        List<Car> cars = carService.findAllCars();
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (!isCarBooked(car) && filter.test(car)) {
                availableCars.add(car);
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
