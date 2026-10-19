package com.dragan.booking;

import com.dragan.exceptions.EntityNotFoundException;

import java.util.Objects;
import java.util.UUID;

public class CarBookingDao {
    private CarBooking[] carBookings = new CarBooking[0];

    public CarBooking[] findAll() {
        return carBookings;
    }

    public CarBooking findById(UUID uuid) throws EntityNotFoundException {
        for (CarBooking booking : carBookings) {
            if (booking != null && Objects.equals(uuid, booking.getId())) {
                return booking;
            }
        }
        throw new EntityNotFoundException("CarBooking", uuid);
    }

    public CarBooking[] findAllBookingsByUserId(UUID userUUID) {
        int count = 0;

        for (CarBooking booking : carBookings) {
            if (booking != null && Objects.equals(userUUID, booking.getUser().getId())) {
                count++;
            }
        }

        CarBooking[] userBookings = new CarBooking[count];
        int index = 0;

        for (CarBooking booking : carBookings) {
            if (booking != null && Objects.equals(userUUID, booking.getUser().getId())) {
                userBookings[index] = booking;
                index++;
            }
        }

        return userBookings;

    }

    public CarBooking save(CarBooking booking) {
        int arrayLength = carBookings.length + 1;
        CarBooking[] extendedArray = new CarBooking[arrayLength];
        extendedArray[arrayLength - 1] = booking;
        carBookings = extendedArray;
        return booking;
    }

}
