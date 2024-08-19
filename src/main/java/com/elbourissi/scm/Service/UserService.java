package com.elbourissi.scm.Service;

import com.elbourissi.scm.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> getUserById(String id);

    Optional<User> update(User user);

    void delete(String id);

    boolean isUserExists(String id);

    boolean isUserExistsByEmail(String email);

    List<User> getAllUsers();

    User getUserByEmail(String email);
}
