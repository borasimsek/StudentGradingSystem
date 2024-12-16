package Repository;

import model.User;

public interface UserRepository {
    User findByEmail(String email);

    void save(User user);

    void delete(User user);

    User findById(String id);

    void update(User user);
}
