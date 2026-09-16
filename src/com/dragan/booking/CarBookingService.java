package com.dragan.booking;

import java.util.UUID;

public class CarBookingService {
    private CarBookingDao carBookingDao = new CarBookingDao();

    public CarBooking[] findAllBookingsByUserId(UUID uuid) {
        return carBookingDao.findAllBookingsByUserId(uuid);
    }

    public CarBooking[] findAll() {
        return carBookingDao.findAll();
    }
}
