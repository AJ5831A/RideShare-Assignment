package com.rideshare.rideV1.exception;

import lombok.Data;

import java.time.Instant;

@Data
public class ErrorResponse {
    private String error;
    private String message;
    private Instant timestamp;
    public ErrorResponse(String err , String msg){
        this.error = err;
        this.message = msg;
        this.timestamp = Instant.now();
    }
}
