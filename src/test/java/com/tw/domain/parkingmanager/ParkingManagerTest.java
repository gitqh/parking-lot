package com.tw.domain.parkingmanager;

import com.google.common.collect.ImmutableList;
import com.tw.domain.ParkingLot;
import com.tw.domain.Ticket;
import com.tw.domain.parkingboy.GraduateParkingBoy;
import com.tw.exception.NotEnoughSpotException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParkingManagerTest {

    @Test
    public void should_return_one_ticket_when_manager_has_one_boy_with_one_lot() {
        List<ParkingLot> parkingLots = ImmutableList.of(new ParkingLot(1));
        ParkingManager parkingManager = new ParkingManager(null, ImmutableList.of(new GraduateParkingBoy(parkingLots)));
        Ticket ticket = parkingManager.park("car-id");
        assertNotNull(ticket);
    }

    @Test
    public void should_return_exception_when_manager_has_one_boy_with_one_full_lot() {
        List<ParkingLot> parkingLots = ImmutableList.of(new ParkingLot(0));
        ParkingManager parkingManager = new ParkingManager(null, ImmutableList.of(new GraduateParkingBoy(parkingLots)));
        Assertions.assertThrows(NotEnoughSpotException.class, () -> parkingManager.park("car-id"));
    }


    @Test
    public void should_return_the_ticket_when_manager_has_one_boy_with_one_empty_lot() {
        ParkingLot parkingLot = new ParkingLot(1);
        List<ParkingLot> parkingLots = ImmutableList.of(parkingLot);
        ParkingManager parkingManager = new ParkingManager(null, ImmutableList.of(new GraduateParkingBoy(parkingLots)));
        Ticket ticket = parkingManager.park("car-id");
        assertEquals(ticket.getParkingLotId(), parkingLot.getId());
    }

    @Test
    public void should_return_the_ticket_when_manager_has_two_boy_and_the_second_boy_has_empty_lot() {
        List<ParkingLot> parkingLotsForBoyA = ImmutableList.of(new ParkingLot(0));
        ParkingLot parkingLot = new ParkingLot(1);
        List<ParkingLot> parkingLotsForBoyB = ImmutableList.of(parkingLot);
        ParkingManager parkingManager = new ParkingManager(null, ImmutableList.of(new GraduateParkingBoy(parkingLotsForBoyA),
                        new GraduateParkingBoy(parkingLotsForBoyB)));
        Ticket ticket = parkingManager.park("car-id");
        assertEquals(ticket.getParkingLotId(), parkingLot.getId());
    }

}