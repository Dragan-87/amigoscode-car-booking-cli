package com.dragan.booking;

import java.util.UUID;

public interface CarBookingDao {

    CarBooking[] findAllBookingsByUserId(UUID userUUID);

    void save(CarBooking carBooking);

    void deleteByID(UUID uuid);

    CarBooking[] findAll();

}
