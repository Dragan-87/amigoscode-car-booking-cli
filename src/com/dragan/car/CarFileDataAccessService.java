package com.dragan.car;

import com.dragan.exceptions.EntityNotFoundException;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CarFileDataAccessService implements CarDao {

    private static final List<Car> DEFAULT_CARS = List.of(
            new Car(UUID.fromString("f3a1c2b4-6d5e-4a1f-9c3b-1a2b3c4d5e01"), "MA MA 605", new BigDecimal("340.00"), Brand.MERCEDES, false),
            new Car(UUID.fromString("7b8c9d0e-1f2a-4b3c-8d4e-5f6a7b8c9d02"), "MA MA 600", new BigDecimal("320.00"), Brand.TESLA, true),
            new Car(UUID.fromString("2c3d4e5f-6a7b-4c8d-9e0f-1a2b3c4d5e03"), "MA MA 590", new BigDecimal("330.00"), Brand.AUDI, false),
            new Car(UUID.fromString("9e8d7c6b-5a4f-4e3d-8c2b-1a0b9c8d7e04"), "MA MA 580", new BigDecimal("290.00"), Brand.TOYOTA, false));

    private final String filePath;

    public CarFileDataAccessService(String filePath) {
        this.filePath = filePath;
    }

    private List<Car> readAll() {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            writeAll(DEFAULT_CARS);
            return new ArrayList<>(DEFAULT_CARS);
        }
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (List<Car>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Can't read cars: " + filePath, e);
        }
    }

    private void writeAll(List<Car> cars) {
        try (
                FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(cars);
        } catch (IOException e) {
            throw new RuntimeException("Can't write in file " + filePath, e);
        }
    }

    @Override
    public List<Car> findAll() {
        return readAll();
    }

    @Override
    public Car findById(UUID carUUID) {
        return readAll().stream().filter(car -> Objects.equals(carUUID, car.getId())).findFirst().orElseThrow(() -> new EntityNotFoundException("Car", carUUID));
    }
}
