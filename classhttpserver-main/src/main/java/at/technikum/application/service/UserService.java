package at.technikum.application.service;

import at.technikum.application.model.User;
import at.technikum.application.repository.UserRepository;

import java.sql.SQLException;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsername(String username) throws SQLException {
        return userRepository.findUserByUsername(username);
    }
}
