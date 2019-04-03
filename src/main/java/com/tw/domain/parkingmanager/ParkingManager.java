package com.tw.domain.parkingmanager;

import com.tw.domain.ParkingLot;
import com.tw.domain.Ticket;
import com.tw.domain.parkingboy.AbstractParkingBoy;
import com.tw.domain.parkingboy.GraduateParkingBoy;
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
