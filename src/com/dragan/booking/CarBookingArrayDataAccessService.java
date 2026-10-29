package com.dragan.booking;

import com.dragan.exceptions.EntityNotFoundException;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDao {
    private CarBooking[] carBookings = new CarBooking[0];

    public CarBooking[] findAll() {
        return carBookings;
    }


    @Override
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

    @Override
    public void save(CarBooking booking) {
        CarBooking[] extendedArray = Arrays.copyOf(carBookings, carBookings.length + 1);
        extendedArray[extendedArray.length - 1] = booking;
        carBookings = extendedArray;
    }

    @Override
    public void deleteByID(UUID uuid) {
        int indexToRemove = -1;

        for (int i = 0; i < carBookings.length; i++) {
            if (Objects.equals(uuid, carBookings[i].getId())) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove == -1) {
            throw new EntityNotFoundException("Booking", uuid);
        }

        CarBooking[] updatedBookings = new CarBooking[carBookings.length - 1];
        int newIndex = 0;
        for (int i = 0; i < carBookings.length; i++) {
            if (i != indexToRemove) {
                updatedBookings[newIndex] = carBookings[i];
                newIndex++;
            }
        }
        carBookings = updatedBookings;
    }

}
