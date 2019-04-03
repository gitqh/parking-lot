package com.tw.domain;

import com.tw.exception.NotEnoughSpotException;
import com.tw.exception.TicketInvalidException;

public interface Parkable {

    Ticket park(String carId) throws NotEnoughSpotException;

    String pick(String ticketId) throws TicketInvalidException;
}
