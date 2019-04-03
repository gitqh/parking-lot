package com.tw.domain;

import com.google.common.collect.Maps;
import com.tw.exception.NotEnoughSpotException;
import com.tw.exception.TicketInvalidException;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParkingLot implements Parkable{
    private String id;

    public int getAvailableCapacity() {
        return capacity - ticketToCarMap.size();
    }



    private int capacity;
    private Map<String, String> ticketToCarMap;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        this.id = initialId();
        ticketToCarMap = Maps.newHashMap();
    }

    public String getId() {
        return id;
    }

    private String initialId() {
        return String.valueOf(RandomUtils.nextInt());
    }

    public boolean hasCapacity() {
        return capacity > ticketToCarMap.size();
    }

    @Override
    public Ticket park(String carId) throws NotEnoughSpotException {
        if(!hasCapacity()) {
            throw new NotEnoughSpotException();
        }
        Ticket ticket = new Ticket(carId, id);
        ticketToCarMap.put(ticket.getId(), carId);
        return ticket;
    }

    @Override
    public String pick(String ticketId) throws TicketInvalidException {
        if (ticketToCarMap.get(ticketId) == null) {
            throw new TicketInvalidException();
        }
        String carId =  ticketToCarMap.get(ticketId);
        ticketToCarMap.remove(ticketId);
        return carId;
    }

    public List<Ticket> inCars(List<String> carIds) throws NotEnoughSpotException {
        return carIds.stream().map(this::park).collect(Collectors.toList());
    }
}