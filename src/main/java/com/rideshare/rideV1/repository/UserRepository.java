package com.rideshare.rideV1.repository;

import com.rideshare.rideV1.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User , String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
