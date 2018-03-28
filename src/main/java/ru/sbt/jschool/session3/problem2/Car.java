package ru.sbt.jschool.session3.problem2;

import java.util.HashMap;

public class Car {
    private String brand;
    private String color;
    private long carID;
    private long timeEntry;

    public Car(String brand, String color, long carID, long timeEntry)
    {
        this.brand = brand;
        this.color = color;
        this.carID = carID;
        this.timeEntry = timeEntry;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public long getCarID() {
        return carID;
    }

    public long getTimeEntry() {
        return timeEntry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (carID != car.carID) return false;
        if (timeEntry != car.timeEntry) return false;
        if (brand != null ? !brand.equals(car.brand) : car.brand != null) return false;
        return color != null ? color.equals(car.color) : car.color == null;
    }

    @Override
    public int hashCode() {
        int result = brand != null ? brand.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (int) (carID ^ (carID >>> 32));
        result = 31 * result + (int) (timeEntry ^ (timeEntry >>> 32));
        return result;
    }
}
