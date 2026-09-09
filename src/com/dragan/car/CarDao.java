package com.dragan.car;

import java.math.BigDecimal;
import java.util.UUID;

public class CarDao {
    private static final Car[] cars;

    static {
        cars = new Car[]{
            new Car(UUID.fromString("a4e07aed-2075-4587-a002-cb9ee30cccd7"),"MA MA 605", new BigDecimal("340.00"), Brand.MERCEDES, false),
            new Car(UUID.fromString("a4e07aed-2075-4587-a002-cb3ee50ccc44"),"MA MA 600", new BigDecimal("320.00"), Brand.TESLA, true),
            new Car(UUID.fromString("a4e07aed-2075-4587-a002-cb3ee50cac21"),"MA MA 590", new BigDecimal("330.00"), Brand.AUDI, false),
            new Car(UUID.fromString("a4e07aed-2075-4587-a002-cb3ee50cac21"),"MA MA 580", new BigDecimal("290.00"), Brand.TOYOTA, false),
        };
    }

}
