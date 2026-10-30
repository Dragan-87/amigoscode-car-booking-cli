package com.dragan.booking;

import com.dragan.exceptions.EntityNotFoundException;

import java.io.*;
import java.util.*;

public class CarBookingFileDataAccessService implements CarBookingDao {

    private final String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    private List<CarBooking> readAll() {
        File file = new File(filePath);
        if (file.length() == 0 || !file.exists()) {
            return new ArrayList<>();
        }
        try(FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (List<CarBooking>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Can't read bookings: " + filePath, e);
        }
    }

    private void writeAll(List<CarBooking> bookings) {
        try (
                FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            throw new RuntimeException("Can't write in file " + filePath, e);
        }
    }


    @Override
    public void save(CarBooking newBooking) {
        List<CarBooking> bookings = readAll();
        bookings.add(newBooking);
        writeAll(bookings);
    }


    @Override
    public void deleteByID(UUID uuid) {
        List<CarBooking> bookings = readAll();
        if (!bookings.removeIf(booking -> Objects.equals(uuid, booking.getId()))) {
            throw new EntityNotFoundException("Booking", uuid);
        }
        writeAll(bookings);
    }

    @Override
    public List<CarBooking> findAll() {
        return readAll();
    }

    @Override
    public List<CarBooking> findAllBookingsByUserId(UUID userUUID) {
        List<CarBooking> bookings = readAll();
        return bookings.stream().filter(booking -> Objects.equals(userUUID, booking.getUser().getId())).toList();
    }


}
