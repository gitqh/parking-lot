package com.tw.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.tw.domain.parking.GraduateParkingBoy;
import com.tw.domain.parking.ParkingLot;
import com.tw.domain.ticket.Ticket;
import com.tw.exception.NotEnoughSpotException;
import com.tw.exception.TicketInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GraduateParkingBoyTest {

    private List<ParkingLot> parkingLots;

    @BeforeEach
    public void initialize() {
        parkingLots = Lists.newArrayList();
    }

    @Test
    public void should_return_ticket_when_parking_lot_has_one_spot() throws NotEnoughSpotException {
        parkingLots.add(new ParkingLot(1));
        GraduateParkingBoy graduateParkingBoy = new GraduateParkingBoy(parkingLots);
        Ticket ticket = graduateParkingBoy.park("car-id");
        assertNotNull(ticket);
    }

    @Test
    public void should_return_multi_tickets_when_first_parking_lot_has_enough_spot()
            throws NotEnoughSpotException {
        parkingLots.add(new ParkingLot(10));
        parkingLots.add(new ParkingLot(1));
        GraduateParkingBoy graduateParkingBoy = new GraduateParkingBoy(parkingLots);
        List<Ticket> tickets = graduateParkingBoy.park(ImmutableList.of("car-id-1", "car-id-2"));
        assertEquals(2, tickets.size());
        assertEquals(tickets.get(0).getParkingLotId(), tickets.get(1).getParkingLotId());
    }

    @Test
    public void should_return_sorted_tickets_when_parking_multi_cars()
            throws NotEnoughSpotException {
        parkingLots.add(new ParkingLot(1));
        parkingLots.add(new ParkingLot(2));
        GraduateParkingBoy graduateParkingBoy = new GraduateParkingBoy(parkingLots);
        List<Ticket> tickets = graduateParkingBoy.park(ImmutableList.of("car-id-1", "car-id-2"));

        assertEquals(parkingLots.get(0).getId(), tickets.get(0).getParkingLotId());
        assertEquals(parkingLots.get(1).getId(), tickets.get(1).getParkingLotId());
    }

    @Test
    public void should_return_multi_tickets_from_second_parking_lot()
            throws NotEnoughSpotException {
        parkingLots.add(new ParkingLot(0));
        parkingLots.add(new ParkingLot(10));

        GraduateParkingBoy graduateParkingBoy = new GraduateParkingBoy(parkingLots);
        List<Ticket> tickets = graduateParkingBoy.park(ImmutableList.of("car-id-1", "car-id-2"));

        assertEquals(parkingLots.get(1).getId(), tickets.get(0).getParkingLotId());
        assertEquals(parkingLots.get(1).getId(), tickets.get(1).getParkingLotId());
    }

    @Test
    public void should_return_exception_when_parking_lots_has_not_enough_spots() {
        parkingLots.add(new ParkingLot(0));
        parkingLots.add(new ParkingLot(0));

        List<Car> cars = ImmutableList.of(new Car());
        List<String> carIds = ImmutableList.of("car-id");

        GraduateParkingBoy graduateParkingBoy = new GraduateParkingBoy(parkingLots);
        Assertions.assertThrows(NotEnoughSpotException.class, () -> graduateParkingBoy.park(carIds));
    }


    @Test
    public void should_return_my_car_when_use_my_ticket_and_only_my_car_in_parking_lot() throws NotEnoughSpotException {
        parkingLots.add(new ParkingLot(1));
        parkingLots.add(new ParkingLot(2));

        GraduateParkingBoy graduateParkingBoy = new GraduateParkingBoy(parkingLots);
        Ticket ticket = graduateParkingBoy.park("my-car");

        for(ParkingLot parkingLot : parkingLots) {
            if (parkingLot.getId().equals(ticket.getParkingLotId())) {
                assertEquals("my-car", ticket.getCarId());
                break;
            }
        }
    }

    @Test
    public void should_return_my_car_when_use_my_ticket() {
        parkingLots.add(new ParkingLot(1));
        parkingLots.add(new ParkingLot(2));

        GraduateParkingBoy graduateParkingBoy = new GraduateParkingBoy(parkingLots);
        graduateParkingBoy.park("car-id-1");
        graduateParkingBoy.park("car-id-2");
        Ticket ticket = graduateParkingBoy.park("my-car");

        for(ParkingLot parkingLot : parkingLots) {
            if (parkingLot.getId().equals(ticket.getParkingLotId())) {
                assertEquals("my-car", ticket.getCarId());
                break;
            }
        }
    }

    @Test
    public void should_return_exception_when_use_invalid_ticket() {
        parkingLots.add(new ParkingLot(1));
        GraduateParkingBoy graduateParkingBoy = new GraduateParkingBoy(parkingLots);
        graduateParkingBoy.park("car-id");
        Assertions.assertThrows(TicketInvalidException.class, () -> graduateParkingBoy.pick("invalid-car-id"));
    }

    @Test
    public void should_return_exception_when_ticket_used_twice()
            throws NotEnoughSpotException, TicketInvalidException {
        parkingLots.add(new ParkingLot(1));
        GraduateParkingBoy graduateParkingBoy = new GraduateParkingBoy(parkingLots);
        Ticket ticket = graduateParkingBoy.park("my-car");

        String myCarOutedId = graduateParkingBoy.pick(ticket.getId());
        assertEquals(myCarOutedId, "my-car");
        Assertions.assertThrows(TicketInvalidException.class, () -> graduateParkingBoy.pick(ticket.getId()));
    }
}
