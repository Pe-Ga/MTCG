/*
package at.technikum.application.repository;

import at.technikum.application.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public User findUserByUsername(String name) {
        return null;
    }
    */
/*public User findUserByUsername(String username) throws SQLException {

        User user = new User();
        Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/swen1db",
                        "swen1user","swen1pw");

        try ( Statement stmt = connection.createStatement())
        {
            String query = "select * from \"User\" where userName=" + username;
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
                user.setUsername(rs.getString("userName"));
                user.setUsername(rs.getString("userBio"));
                user.setUsername(rs.getString("userImage"));
            }

        }
        return user;
    }*//*


    @Override
    public List<User> findAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();


        Connection connection = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/swen1db",
                "swen1user","swen1pw");

        try (Statement smt = connection.createStatement())
        {
            String query = "select * from \"User\" ";
            ResultSet rs = smt.executeQuery(query);
            while(rs.next())
            {
                User user = new User();
                user.setUsername(rs.getString("userName"));
                userList.add(user);
            }
            rs.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        connection.close();
        return userList;
    }

    @Override
    public User registerUser(String username, String password) {
        return null;
    }

    @Override
    public User updateUserByUsername(String username) {
        return null;
    }

    @Override
    public void deleteUserById(int id) {

    }

    @Override
    public void save(User user) {

    }



}
*/
