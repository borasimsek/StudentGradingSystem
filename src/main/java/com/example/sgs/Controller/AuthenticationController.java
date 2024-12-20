package com.example.sgs.Controller;

import com.example.sgs.Entities.User.UserType;
import com.example.sgs.Service.AuthenticationService;

public class AuthenticationController {

    private AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    public void register(String email, String password, UserType userType, String firstName, String lastName) {
        boolean success = authService.register(email, password, userType, firstName, lastName);
        if (success) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Failed to register user.");
        }
    }
}
