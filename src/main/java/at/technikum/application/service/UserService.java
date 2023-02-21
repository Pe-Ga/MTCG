package at.technikum.application.service;

import at.technikum.application.model.User;
import at.technikum.application.repository.IUserRepository;

import java.sql.SQLException;

public class UserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsername(String username) throws SQLException {
        return userRepository.findUser(username);
    }
}
