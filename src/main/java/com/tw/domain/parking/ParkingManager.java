package com.tw.domain.parking;

import com.tw.domain.ticket.Ticket;
import com.tw.exception.NotEnoughSpotException;

import java.util.List;

public class ParkingManager extends GraduateParkingBoy {

    private List<AbstractParkingBoy> parkingBoys;

    public ParkingManager(List<ParkingLot> parkingLots, List<AbstractParkingBoy> parkingBoys) {
        super(parkingLots);
        this.parkingBoys = parkingBoys;
    }

    @Override
    public Ticket park(String carId) {
        return parkingBoys.stream().filter(parkingBoy -> parkingBoy.hasEnoughParkingSpot(carId))
                .findFirst().map(parkingBoy -> parkingBoy.park(carId))
                .orElseThrow(NotEnoughSpotException::new);
    }
}
