package at.technikum.application.repository;

import at.technikum.application.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserRepository {

    User findUser(String username) throws SQLException;
    User findUserByToken(String username) throws SQLException;
    List<User> findAllUsers() throws SQLException;

    boolean loginIsValid(String username, String userpassword);
    boolean registerUser(User user) throws SQLException;
    //Retrieves the user data for the username provided in the route.
    // Only the admin or the matching user
    // can successfully retrieve the data.
    boolean updateUser(User user) throws SQLException;

}
