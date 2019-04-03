package com.tw.adapter.persistence.jpa.repository;

import com.google.common.collect.ImmutableList;
import com.tw.domain.Car;
import com.tw.domain.parking.repository.ParkingLotRepository;

import java.util.List;

public class ParkingLotRepositoryIml implements ParkingLotRepository {
    @Override
    public int findAvailableCapacityById(String id) {
        return 1;
    }

    @Override
    public List<String> findCarIdsById(String id) {
        return ImmutableList.of("川A88888", "川A88889");
    }

    @Override
    public boolean park(Car car) {
        return true;
    }
}
