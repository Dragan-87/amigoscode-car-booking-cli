package com.dragan.booking;

import com.dragan.exceptions.EntityNotFoundException;

import java.util.*;

public class CarBookingArrayDataAccessService implements CarBookingDao {
    private List<CarBooking> carBookings = new ArrayList<>();

    public List<CarBooking> findAll() {
        return new ArrayList<>(carBookings);
    }


    @Override
    public List<CarBooking> findAllBookingsByUserId(UUID userUUID) {
        List<CarBooking> userBookings = new ArrayList<>();
        for (CarBooking booking : carBookings) {
            if (booking != null && Objects.equals(userUUID, booking.getUser().getId())) {
                userBookings.add(booking);
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
       if (!carBookings.removeIf(booking -> booking != null && Objects.equals(uuid, booking.getId()))){
           throw new EntityNotFoundException("Booking", uuid);
       }

    }

}
