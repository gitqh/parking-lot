package com.tw.domain.parkingboy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.tw.domain.Car;
import com.tw.domain.ParkingLot;
import com.tw.domain.Ticket;
import com.tw.exception.NotEnoughSpotException;
import com.tw.exception.TicketInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SmartParkingBoyTest {

    private List<ParkingLot> parkingLots;

    @BeforeEach
    public void initialize() {
        parkingLots = Lists.newArrayList();
    }

    @Test
    public void should_return_ticket_when_parking_lot_has_one_spot() throws NotEnoughSpotException {
        parkingLots.add(new ParkingLot(1));
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Ticket ticket = smartParkingBoy.park("car-id");
        assertNotNull(ticket);
    }

    @Test
    public void should_return_one_ticket_when_first_parking_lot_has_more_spot()
            throws NotEnoughSpotException {
        ParkingLot parkingLot1 = new ParkingLot(10);
        ParkingLot parkingLot2 = new ParkingLot(1);
        parkingLots.add(parkingLot1);
        parkingLots.add(parkingLot2);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Ticket ticket = smartParkingBoy.park("car-id");
        assertEquals(parkingLot1.getId(), ticket.getParkingLotId());
    }

    @Test
    public void should_return_one_ticket_when_second_parking_lot_has_more_spot()
            throws NotEnoughSpotException {
        ParkingLot parkingLot1 = new ParkingLot(10);
        ParkingLot parkingLot2 = new ParkingLot(11);
        parkingLots.add(parkingLot1);
        parkingLots.add(parkingLot2);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Ticket ticket = smartParkingBoy.park("car-id");
        assertEquals(parkingLot2.getId(), ticket.getParkingLotId());
    }

    @Test
    public void should_return_tickets_when_two_parking_lot_has_same_spot()
            throws NotEnoughSpotException {
        parkingLots.add(new ParkingLot(2));
        parkingLots.add(new ParkingLot(2));

        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        Car car4 = new Car();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        List<Ticket> tickets = smartParkingBoy.park(ImmutableList.of("car-id-1",
                "car-id-2", "car-id-3", "car-id-4"));

        assertEquals(parkingLots.get(0).getId(), tickets.get(0).getParkingLotId());
        assertEquals(parkingLots.get(1).getId(), tickets.get(1).getParkingLotId());
        assertEquals(parkingLots.get(0).getId(), tickets.get(2).getParkingLotId());
        assertEquals(parkingLots.get(1).getId(), tickets.get(3).getParkingLotId());
    }

    @Test
    public void should_return_exception_when_two_parking_lots_have_not_enough_spots() {
        parkingLots.add(new ParkingLot(0));
        parkingLots.add(new ParkingLot(0));

        List<String> carIds = ImmutableList.of("car-id");

        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Assertions.assertThrows(NotEnoughSpotException.class, () -> smartParkingBoy.park(carIds));
    }


    @Test
    public void should_return_my_car_when_use_my_ticket_and_only_my_car_in_parking_lot() throws NotEnoughSpotException {
        parkingLots.add(new ParkingLot(1));
        parkingLots.add(new ParkingLot(2));

        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Ticket ticket = smartParkingBoy.park("car-id");

        for(ParkingLot parkingLot : parkingLots) {
            if (parkingLot.getId().equals(ticket.getParkingLotId())) {
                assertEquals("car-id", ticket.getCarId());
                break;
            }
        }
    }

    @Test
    public void should_return_my_car_when_use_my_ticket() {
        parkingLots.add(new ParkingLot(1));
        parkingLots.add(new ParkingLot(2));

        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        smartParkingBoy.park("car-id-1");
        smartParkingBoy.park("car-id-2");
        Ticket ticket = smartParkingBoy.park("my-car");

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
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        smartParkingBoy.park("car-id");
        Assertions.assertThrows(TicketInvalidException.class, () -> smartParkingBoy.pick("invalid-car-id"));
    }

    @Test
    public void should_return_exception_when_ticket_used_twice()
            throws NotEnoughSpotException, TicketInvalidException {
        parkingLots.add(new ParkingLot(1));
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLots);
        Ticket ticket = smartParkingBoy.park("car-id");

        String myCarOutedId = smartParkingBoy.pick(ticket.getId());
        assertEquals(myCarOutedId, "car-id");
        Assertions.assertThrows(TicketInvalidException.class, () -> smartParkingBoy.pick(ticket.getId()));
    }
}