package com.rideshare.rideV1.service;

import com.rideshare.rideV1.dto.RideRequest;
import com.rideshare.rideV1.dto.RideResponse;
import com.rideshare.rideV1.exception.BadRequestException;
import com.rideshare.rideV1.exception.NotFoundException;
import com.rideshare.rideV1.model.Ride;
import com.rideshare.rideV1.model.RideStatus;
import com.rideshare.rideV1.model.User;
import com.rideshare.rideV1.model.UserRole;
import com.rideshare.rideV1.repository.RideRepository;
import com.rideshare.rideV1.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {
    private final RideRepository rideRepository;
    private final UserRepository userRepository;


    public RideService(RideRepository rideRepository, UserRepository userRepository) {
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }

    public RideResponse createRide(String username , RideRequest request){
        User user = getUserByUsername(username);
        if(user.getRole() != UserRole.USER){
            throw new BadRequestException("Only passengers can request ride");
        }

        Ride ride = new Ride(user.getId(), request.getPickupLocation(), request.getDropLocation());
        Ride saved = rideRepository.save(ride);
        return new RideResponse(saved);
    }

    public List<RideResponse> getPendingRideRequests(){
        return rideRepository.findByStatus(String.valueOf(RideStatus.REQUESTED))
                .stream()
                .map(RideResponse::new)
                .collect(Collectors.toList());
    }

    public RideResponse acceptRide(String username, String rideId){
        User driver = getUserByUsername(username);

        if(driver.getRole() != UserRole.DRIVER){
            throw new BadRequestException("Only drivers can accept rides");
        }

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(()-> new NotFoundException("Ride not found"));

        if(ride.getStatus()!=RideStatus.REQUESTED){
            throw new BadRequestException("Ride is not in requested state");
        }

        ride.setDriverId(driver.getId());
        ride.setStatus(RideStatus.ACCEPTED);

        Ride saved = rideRepository.save(ride);
        return new RideResponse(saved);
    }

    public RideResponse completeRide(String username , String rideId){
        User caller = getUserByUsername(username);
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));

        if(ride.getStatus() != RideStatus.ACCEPTED){
            throw new BadRequestException("Ride is not in Accepted state");
        }

        ride.setStatus(RideStatus.ACCEPTED);
        Ride saved = rideRepository.save(ride);
        return new RideResponse(saved);
    }

    public List<RideResponse> getUserRides(String username){
        User user = getUserByUsername(username);
        return rideRepository.findByUserId(user.getId())
                .stream()
                .map(RideResponse::new)
                .collect(Collectors.toList());
    }

    private User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: "+username));
    }
}
