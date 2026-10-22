package com.dragan.booking;

import com.dragan.exceptions.EntityNotFoundException;

import java.io.*;
import java.util.*;

public class CarBookingFileDataAccessService implements CarBookingDao {

    String filePath;

    public CarBookingFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    private List<CarBooking> readAll() {
        File file = new File(filePath);
        if (!file.exists()) {
            return List.of();
        }

        try(FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (List<CarBooking>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Can't read bookings: " + filePath , e);
        }
    }

    private void writeAll(List<CarBooking> bookings) {
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
        List<CarBooking> updatedList = new ArrayList<>(readAll());
        updatedList.add(newBooking);
        writeAll(updatedList);
    }

    @Override
    public void deleteByID(UUID uuid) {
        List<CarBooking> updatedList = new ArrayList<>(readAll());
        if (!updatedList.removeIf(booking -> Objects.equals(booking.getId(), uuid))) {
            throw new EntityNotFoundException("Booking", uuid);
        }
        writeAll(updatedList);

    }

    @Override
    public List<CarBooking> findAll() {
        return readAll();
    }

    @Override
    public List<CarBooking> findAllBookingsByUserId(UUID userUUID) {
        List<CarBooking> bookings = readAll();
        List<CarBooking> userBookings = new ArrayList<>();

        for (CarBooking booking : bookings) {
            if (Objects.equals(userUUID, booking.getUser().getId())) {
                userBookings.add(booking);
            }
        }

        return userBookings;
    }


}
