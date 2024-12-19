package com.example.sgs.Service;

import com.example.sgs.model.User;

public interface UserService {
        boolean authenticate(String studentNumber, String password);
        boolean changePassword(String studentNumber, String oldPassword, String newPassword);
        User findUserByEmail(String email);
        void updatePassword(String userId, String newPassword);
}
