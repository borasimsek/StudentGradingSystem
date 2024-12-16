package Service;

import Repository.UserRepository;
import model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String encryptedPassword = EncryptionUtil.encrypt(password);
            return user.getPassword().equals(encryptedPassword);
        }
        return false;
    }

    public User findUserById(String userId) {
        return userRepository.findById(userId);
    }

    public void registerUser(User user) {
        user.setPassword(EncryptionUtil.encrypt(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(User user) {
        if (user.getPassword() != null) {
            user.setPassword(EncryptionUtil.encrypt(user.getPassword()));
        }

        userRepository.update(user);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.delete(userId);
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
