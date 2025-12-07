package com.rideshare.rideV1.dto;

import jakarta.validation.constraints.NotBlank;

public class RideRequest {
    @NotBlank(message = "Pickup location is required")
    private String pickupLocation;
    @NotBlank(message = "Drop location is required")
    private String dropLocation;
}
