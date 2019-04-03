package com.tw.domain;

import com.google.common.collect.ImmutableList;
import com.tw.domain.parking.ParkingLot;
import com.tw.domain.ticket.Ticket;
import com.tw.exception.NotEnoughSpotException;
import com.tw.exception.TicketInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParkingLotTest {

    @Test
    public void should_return_one_ticket_when_parking_lot_has_a_spot() throws NotEnoughSpotException {
        ParkingLot parkingLot = new ParkingLot(1);
        Ticket ticket = parkingLot.park("car-id");
        assertNotNull(ticket);
    }

    @Test
    public void should_return_multiple_ticket_when_parking_multiple_car_and_parking_lot_has_multiple_spot()
            throws NotEnoughSpotException {
        ParkingLot parkingLot = new ParkingLot(5);
        List<Ticket> tickets = parkingLot.inCars(ImmutableList.of("car-id-1", "car-id-2"));
        Set<Ticket> ticketSet = new HashSet<>(tickets);
        assertEquals(2, ticketSet.size());
    }

    @Test
    public void should_return_exception_when_parking_lot_has_not_enough_spot() {
        ParkingLot parkingLot = new ParkingLot(0);
        Assertions.assertThrows(NotEnoughSpotException.class, () -> parkingLot.park("car-id"));
    }

    @Test
    public void should_return_the_car_when_parking_lot_has_only_one_car()
            throws NotEnoughSpotException, TicketInvalidException {
        ParkingLot parkingLot = new ParkingLot(1);
        Ticket ticket = parkingLot.park("car-id");
        String carOutedId = parkingLot.pick(ticket.getId());
        assertEquals("car-id", carOutedId);
    }

    @Test
    public void should_return_my_car_when_parking_lot_has_cars()
            throws NotEnoughSpotException, TicketInvalidException {
        ParkingLot parkingLot = new ParkingLot(3);
        parkingLot.inCars(ImmutableList.of("car-id-1", "car-id-2"));

        Ticket ticket = parkingLot.park("my-car");

        String myCarOutedId = parkingLot.pick(ticket.getId());

        assertEquals("my-car", myCarOutedId);
    }

    @Test
    public void should_return_exception_when_ticket_invalid()
            throws NotEnoughSpotException {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park("my-car");
        Assertions.assertThrows(TicketInvalidException.class, () -> parkingLot.pick("invalid-car-id"));
    }

    @Test
    public void should_return_exception_when_ticket_used_twice()
            throws NotEnoughSpotException, TicketInvalidException {
        ParkingLot parkingLot = new ParkingLot(1);
        Ticket ticket = parkingLot.park("my-car");

        String myCarOutedId = parkingLot.pick(ticket.getId());
        assertEquals(myCarOutedId, "my-car");

        Assertions.assertThrows(TicketInvalidException.class, () -> parkingLot.pick(ticket.getId()));
    }

    @Test
    public void should_return_exception_when_in_car_multiple() throws NotEnoughSpotException {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park("car-id");
        Assertions.assertThrows(NotEnoughSpotException.class, () -> parkingLot.park("car-id"));
    }

    @Test
    public void should_return_the_car_when_out_car_multiple()
            throws NotEnoughSpotException, TicketInvalidException {
        ParkingLot parkingLot = new ParkingLot(1);
        Ticket ticket = parkingLot.park("car-id");
        parkingLot.pick(ticket.getId());
        Ticket ticketForNewCar = parkingLot.park("new-car-id");
        String newCarOutedId = parkingLot.pick(ticketForNewCar.getId());
        assertEquals("new-car-id", newCarOutedId);
    }

}
