package com.dragan.booking;
import com.dragan.car.Car;
import com.dragan.car.CarService;
import com.dragan.user.User;
import com.dragan.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        return carBookings.stream().anyMatch(booking -> Objects.equals(car.getId(), booking.getCar().getId()));
    }

    public List<Car> getAvailableCars(Predicate<Car> filter) {
        List<Car> cars = carService.findAllCars();
        return cars.stream().filter(car -> !isCarBooked(car) && filter.test(car)).toList();
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
