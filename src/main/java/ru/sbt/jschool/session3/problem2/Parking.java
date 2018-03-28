package ru.sbt.jschool.session3.problem2;

import java.util.HashMap;

public class Parking {

    private long capacity;
    private long pricePerHour;

    HashMap<Long, Car> places = new HashMap<>();

    public Parking(long capacity, long pricePerHour)
    {
        this.capacity = capacity;
        this.pricePerHour = pricePerHour;
    }

    public boolean chanceToEntry(long carID, long timeEntry, String brand, String color)
    {
        if (places.get(carID)==null&&places.size()!=capacity) {
            places.put(carID, new Car(brand, color, carID, timeEntry));
            return true;
        }
        return false;
    }

    public float exit(long carId, long timeExit)
    {
        Car car = places.get(carId);
        places.remove(carId);

        long timeOfDoublePrice = 0;
        long timeEntry = car.getTimeEntry();
        long timeOfStaying = timeExit - timeEntry;

        for (long i = timeEntry; i <= timeExit ; i++) {
            long rem = i%24;
            if (rem>=23||rem<=6)
                timeOfDoublePrice++;
        }
        long allPrice = timeOfStaying*pricePerHour + timeOfDoublePrice*pricePerHour;

        return allPrice;
    }
}
