package com.tw.application;

import com.tw.domain.parking.repository.ParkingLotRepository;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ParkingApplicationService {

    private ParkingLotRepository parkingLotRepository;

    public ParkingApplicationService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    private List<String> getCars(String parkingLotId) {
        return parkingLotRepository.findCarIdsById(parkingLotId);
    }

    private int getAvailableCapacity(String parkingLotId) {
        return parkingLotRepository.findAvailableCapacityById(parkingLotId);
    }

    public String showStatus(String parkingLotId) {
        int c = getAvailableCapacity(parkingLotId);
        return String.format("Available capacity:%s\t\nCar Ids:\n%s", getAvailableCapacity(parkingLotId), StringUtils.join(getCars(parkingLotId), "\n"));
    }
}
