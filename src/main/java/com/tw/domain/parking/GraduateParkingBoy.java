package com.tw.domain.parking;

import com.tw.domain.ticket.Ticket;
import com.tw.exception.NotEnoughSpotException;

import java.util.List;

public class GraduateParkingBoy extends AbstractParkingBoy {

    public GraduateParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    public Ticket park(String carId) throws NotEnoughSpotException {
        return parkingLots.stream()
                .filter(ParkingLot::hasCapacity)
                .findFirst().map(parkingLot -> parkingLot.park(carId))
                .orElseThrow(NotEnoughSpotException::new);
    }

}
