package com.rideshare.rideV1.dto;

import com.rideshare.rideV1.model.RideStatus;
import lombok.Data;

import java.util.Date;

@Data
public class RideResponse {
    private String id;
    private String userId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private RideStatus status;
    private Date createdAt;
}
