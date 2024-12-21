package com.example.sgs.Service;

import com.example.sgs.Repository.UserRepository;
import com.example.sgs.Entities.User;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public boolean saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getStudentsByInstructorAndTerm(int instructorId, int year, String term) {
        return userRepository.findStudentsByInstructorAndTerm(instructorId, year, term);
    }
}
