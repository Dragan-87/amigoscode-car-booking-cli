package com.dragan.car;

import com.dragan.booking.CarBooking;
import com.dragan.exceptions.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class CarDao {
    private static final Car[] cars;

    static {
        cars = new Car[]{
                new Car(UUID.fromString("f3a1c2b4-6d5e-4a1f-9c3b-1a2b3c4d5e01"), "MA MA 605", new BigDecimal("340.00"), Brand.MERCEDES, false),
                new Car(UUID.fromString("7b8c9d0e-1f2a-4b3c-8d4e-5f6a7b8c9d02"), "MA MA 600", new BigDecimal("320.00"), Brand.TESLA, true),
                new Car(UUID.fromString("2c3d4e5f-6a7b-4c8d-9e0f-1a2b3c4d5e03"), "MA MA 590", new BigDecimal("330.00"), Brand.AUDI, false),
                new Car(UUID.fromString("9e8d7c6b-5a4f-4e3d-8c2b-1a0b9c8d7e04"), "MA MA 580", new BigDecimal("290.00"), Brand.TOYOTA, false),
        };
    }

    public Car[] findAll() {
        return cars;
    }

    public Car findById(UUID uuid) throws EntityNotFoundException {
        for (Car car : cars) {
            if (car != null && Objects.equals(uuid, car.getId())) {
                return car;
            }
        }
        throw new EntityNotFoundException("Car", uuid);
    }


}
