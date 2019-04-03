package com.tw.domain.parking.repository;

import com.tw.domain.Car;

import java.util.List;

public interface ParkingLotRepository {

    int findAvailableCapacityById(String id);

    List<String> findCarIdsById(String id);

    boolean park(Car car);
}
