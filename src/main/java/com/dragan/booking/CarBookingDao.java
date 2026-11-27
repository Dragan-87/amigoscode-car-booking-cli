package com.dragan.booking;

import java.util.List;
import java.util.UUID;

public interface CarBookingDao {

    List<CarBooking> findAllBookingsByUserId(UUID userUUID);

    void save(CarBooking carBooking);

    void deleteByID(UUID uuid);

    List<CarBooking> findAll();

}
