package com.tw.adapter.persistence.jpa.repository;

import com.tw.domain.ticket.Ticket;
import com.tw.domain.ticket.repository.TicketRepository;

public class TicketRepositoryIml implements TicketRepository {
    @Override
    public Ticket findByCarId(String carId) {
        return new Ticket("川A88888", "parking-lot-id");
    }

    @Override
    public boolean save(Ticket ticket) {
        return true;
    }
}
