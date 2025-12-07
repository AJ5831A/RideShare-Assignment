package com.rideshare.rideV1.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "ride")
@Data
public class Ride {
//    Ride
// ├─ id : String
// ├─ userId : String         → Passenger (FK)
// ├─ driverId : String?      → Driver (FK)
// ├─ pickupLocation : String
// ├─ dropLocation : String
// ├─ status : String         → REQUESTED / ACCEPTED / COMPLETED
// ├─ createdAt : Date

    @Id
    private String id;
    private String userId;
    private String driverId;
    private String pickupLocation;
    private String dropLocation;
    private RideStatus status = RideStatus.REQUESTED;
    @CreatedDate
    private Date createdAt;
}
