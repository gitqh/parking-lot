package com.tw.domain.ticket.repository;

import com.tw.domain.ticket.Ticket;

public interface TicketRepository {

    Ticket findByCarId(String carId);

    boolean save(Ticket ticket);

}
