package com.example.sgs.Controller;

import com.example.sgs.Service.UserService;
import com.example.sgs.View.LoginPage;
import com.example.sgs.Entities.User;

import java.util.HashMap;

public class AuthenticationController {
    private final UserService userService;
    private final LoginPage loginView;
    private final HashMap<String, Integer> loginAttempts = new HashMap<>();
    private final HashMap<String, Long> lockoutTimestamps = new HashMap<>();
    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCKOUT_DURATION = 15 * 60 * 1000; // 15 MIN WAIT TIME

    public AuthenticationController(UserService userService, LoginPage loginView) {
        this.userService = userService;
        this.loginView = loginView;
    }



    private boolean isAccountLocked(String username) {
        if (lockoutTimestamps.containsKey(username)) {
            long lockedUntil = lockoutTimestamps.get(username);
            if (System.currentTimeMillis() < lockedUntil) {
                return true;
            }
            lockoutTimestamps.remove(username);
        }
        return false;
    }

    private void incrementLoginAttempts(String username) {
        loginAttempts.put(username, loginAttempts.getOrDefault(username, 0) + 1);
        if (loginAttempts.get(username) >= MAX_ATTEMPTS) {
            lockoutTimestamps.put(username, System.currentTimeMillis() + LOCKOUT_DURATION);
            loginAttempts.remove(username);
            System.out.println("Account locked due to too many failed attempts. Try again later.");
        }
    }

    /*
    public void login(String username, String password) {

        if (isAccountLocked(username)) {
            loginView.displayError("Account is locked. Try again later.");
            return;
        }

        User user = userService.authenticate(username, password);
        if (user != null) {
            resetLoginAttempts(username);
            loginView.displaySuccess("Login successful! Welcome, " + username);
        } else {
            incrementLoginAttempts(username);
            loginView.displayError("Invalid credentials. Please try again.");
        }
    }

    */

    private void resetLoginAttempts(String username) {
        loginAttempts.remove(username);
        lockoutTimestamps.remove(username);
    }
}

