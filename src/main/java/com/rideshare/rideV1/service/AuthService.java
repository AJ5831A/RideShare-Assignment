package com.rideshare.rideV1.service;

import com.rideshare.rideV1.dto.AuthResponse;
import com.rideshare.rideV1.dto.LoginRequest;
import com.rideshare.rideV1.dto.RegisterRequest;
import com.rideshare.rideV1.exception.BadRequestException;
import com.rideshare.rideV1.model.User;
import com.rideshare.rideV1.repository.UserRepository;
import com.rideshare.rideV1.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new BadRequestException("Username already exists");
        }
        if(request.getRole()==null){
            throw new BadRequestException("Role should not be null");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request){
        var authToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        authenticationManager.authenticate(authToken);
        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(token);
    }
}
