package com.tw.domain.ticket;

import org.apache.commons.lang3.RandomUtils;

public class Ticket {

    private String id;

    private String carId;

    private String parkingLotId;

    public String getId() {
        return id;
    }

    public String getCarId() {
        return carId;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public Ticket(String carId, String parkingLotId) {
        this.carId = carId;
        this.parkingLotId = parkingLotId;
        this.id = initialId();
    }

    private String initialId() {
        return String.valueOf(RandomUtils.nextInt());
    }

}
