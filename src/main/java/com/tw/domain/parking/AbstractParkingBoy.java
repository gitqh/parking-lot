package com.tw.domain.parking;

import com.tw.domain.ticket.Ticket;
import com.tw.exception.NotEnoughSpotException;
import com.tw.exception.TicketInvalidException;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractParkingBoy implements Parkable {

    List<ParkingLot> parkingLots;

    AbstractParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public boolean hasEnoughParkingSpot(List<String> carIds) {
        return parkingLots.stream().filter(ParkingLot::hasCapacity)
                .map(ParkingLot::getAvailableCapacity)
                .reduce(0, Integer::sum).compareTo(carIds.size()) >= 0;
    }

    public boolean hasEnoughParkingSpot(String carId) {
        return parkingLots.stream().filter(ParkingLot::hasCapacity)
                .map(ParkingLot::getAvailableCapacity)
                .reduce(0, Integer::sum).compareTo(1) >= 0;
    }

    public List<Ticket> park(List<String> carIds) throws NotEnoughSpotException {
        if (!hasEnoughParkingSpot(carIds)) {
            throw new NotEnoughSpotException();
        }
        return carIds.stream().map(this::park).collect(Collectors.toList());
    }

    @Override
    public String pick(String ticketId) {
        return parkingLots.stream().map(p -> p.pick(ticketId)).findFirst().orElseThrow(TicketInvalidException::new);
    }

    @Override
    public abstract Ticket park(String carId) throws NotEnoughSpotException;


//    public Ticket createTicket(String ticketId) {
//        return new Ticket(ticketId);
//    }

}
