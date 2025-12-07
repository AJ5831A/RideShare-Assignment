package com.rideshare.rideV1.dto;

import com.rideshare.rideV1.model.Ride;
import com.rideshare.rideV1.model.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideResponse {
    private String id;
    private String userId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private RideStatus status;
    private Date createdAt;

    public RideResponse(Ride ride) {
        this.id = ride.getId();
        this.userId = ride.getUserId();
        this.driverId = ride.getDriverId();
        this.pickupLocation = ride.getPickupLocation();
        this.dropLocation = ride.getDropLocation();
        this.status = ride.getStatus();
        this.createdAt = ride.getCreatedAt();
    }
}
