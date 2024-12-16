package Service;

import model.User;
import java.util.List;

public interface UserService {

    boolean authenticate(String email, String password);
    void registerUser(User user);
    void updateUser(User user);
    void deleteUser(String userId);
    User getUserById(String userId);
    List<User> getAllUsers();
}




