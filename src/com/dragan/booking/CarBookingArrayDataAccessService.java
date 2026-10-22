package com.dragan.booking;

import com.dragan.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CarBookingArrayDataAccessService implements CarBookingDao {
    private final List<CarBooking> carBookings = new ArrayList<>();

    public List<CarBooking> findAll() {
        return carBookings;
    }


    @Override
    public List<CarBooking> findAllBookingsByUserId(UUID userUUID) {
        List<CarBooking> userBookings = new ArrayList<>();
        for (CarBooking carBooking : carBookings) {
            if (Objects.equals(carBooking.getId(), userUUID)) {
                userBookings.add(carBooking);
            }
        }

        return userBookings;

    }

    @Override
    public void save(CarBooking booking) {
        carBookings.add(booking);
    }

    @Override
    public void deleteByID(UUID uuid) {
        if (!carBookings.removeIf(booking -> Objects.equals(booking.getId(), uuid))) {
            throw new EntityNotFoundException("Booking", uuid);
        }
    }

}
