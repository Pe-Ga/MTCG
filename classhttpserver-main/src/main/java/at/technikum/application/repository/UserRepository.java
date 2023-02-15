package at.technikum.application.repository;

import at.technikum.application.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {

    User findUserByUsername(String username) throws SQLException;
    List<User> findAllUsers() throws SQLException;
    boolean registerUser(String username, String password) throws SQLException;
    //Retrieves the user data for the username provided in the route.
    // Only the admin or the matching user
    // can successfully retrieve the data.
    User updateUserByUsername(String username);
    void deleteUserById(int id);
    void save(User user);

}
