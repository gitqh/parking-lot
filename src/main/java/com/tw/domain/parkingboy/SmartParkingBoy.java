package com.tw.domain.parkingboy;

import com.tw.domain.ParkingLot;
import com.tw.domain.Ticket;
import com.tw.exception.NotEnoughSpotException;

import java.util.Comparator;
import java.util.List;

public class SmartParkingBoy extends AbstractParkingBoy {

    public SmartParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    public Ticket park(String carId) throws NotEnoughSpotException {
        Ticket ticket = parkingLots.stream()
                .filter(ParkingLot::hasCapacity)
                .max(Comparator.comparingInt(ParkingLot::getAvailableCapacity))
                .map(parkingLot -> parkingLot.park(carId))
                .orElseThrow(NotEnoughSpotException::new);

        parkingLots.stream().filter(ParkingLot::hasCapacity)
                .sorted(Comparator.comparingInt(ParkingLot::getAvailableCapacity));

        return ticket;
    }
}
