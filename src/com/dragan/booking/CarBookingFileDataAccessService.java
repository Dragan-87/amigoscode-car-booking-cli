package com.dragan.booking;

import com.dragan.exceptions.EntityNotFoundException;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDao {

    String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    private CarBooking[] readAll() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new CarBooking[0];
        }
        try(FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (CarBooking[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Can't read bookings: " + filePath , e);
        }
    }

    private void writeAll(CarBooking[] bookings) {
        try (
                FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            throw new RuntimeException("Can't write in file " + filePath ,e);
        }
    }


    @Override
    public void save(CarBooking newBooking) {
        CarBooking[] bookings = readAll();
        CarBooking[] newBookings = Arrays.copyOf(bookings, bookings.length + 1);
        newBookings[newBookings.length - 1] = newBooking;
        writeAll(newBookings);
    }


    @Override
    public void deleteByID(UUID uuid) {
        CarBooking[] bookings = readAll();
        int newArrayIndex = 0;
        int deletingIndex = -1;
        for (int i = 0; i < bookings.length; i++) {
            if (Objects.equals(uuid, bookings[i].getId())) {
                deletingIndex = i;
                break;
            }
        }

        if (deletingIndex < 0) {
            throw new EntityNotFoundException("Booking", uuid);
        }

        CarBooking[] updatedBookings = new CarBooking[bookings.length - 1];
        for (int i = 0; i < bookings.length; i++) {
            if (i == deletingIndex) {
                continue;
            }
            updatedBookings[newArrayIndex] = bookings[i];
            newArrayIndex++;
        }
        writeAll(updatedBookings);
    }

    @Override
    public CarBooking[] findAll() {
        return readAll();
    }

    @Override
    public CarBooking[] findAllBookingsByUserId(UUID userUUID) {
        CarBooking[] bookings = readAll();
        for (CarBooking booking : bookings) {
            if (Objects.equals(userUUID, booking.getId())) {
                return bookings;
            }
        }
        throw new EntityNotFoundException("User", userUUID);
    }


}
