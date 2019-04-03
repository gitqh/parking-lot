package com.tw.domain.parking;

import com.tw.domain.ticket.Ticket;
import com.tw.exception.NotEnoughSpotException;
import com.tw.exception.TicketInvalidException;

public interface Parkable {

    Ticket park(String carId) throws NotEnoughSpotException;

    String pick(String ticketId) throws TicketInvalidException;
}
