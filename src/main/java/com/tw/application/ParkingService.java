package com.tw.application;

import com.tw.domain.Parkable;
import com.tw.domain.Ticket;

public class ParkingService {

    private Parkable parkingBoy;

    private Ticket ticket;

    public ParkingService(Parkable parkingBoy, Ticket ticket) {
        this.parkingBoy = parkingBoy;
        this.ticket = ticket;
    }

    public void park(String carId) {
        parkingBoy.park(carId);
    }

    public void pick(String carId) {
        parkingBoy.pick(carId);
    }
}
