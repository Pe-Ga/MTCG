package at.technikum.application.repository;

import at.technikum.application.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public User findUserByUsername(String username) {
        return null;
    }

    @Override
    public List<User> findAllUsers()
    {
        List<User> userList = new ArrayList<>();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/swen1db",
                    "swen1user","swen1pw");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = "select * from \"User\" ";
        try {
            Statement smt = con.createStatement();
            ResultSet rs = smt.executeQuery(query);
            while(rs.next())
            {
                User user = new User();
                user.setUsername(rs.getString("userName"));
                userList.add(user);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        try
        {
            con.close();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

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
