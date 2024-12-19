package com.example.sgs.Service;
import com.example.sgs.Repository.UserRepository;
import com.example.sgs.model.User;

public abstract class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public void updatePassword(String userId, String newPassword) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.update(user);
        }
    }
    @Override
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId);

        // Check if user exists and the old password matches
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword); // Update the password
            userRepository.update(user);   // Save changes to the repository
            return true; // Password successfully changed
        }
        return false; // Password change failed
    }
}

