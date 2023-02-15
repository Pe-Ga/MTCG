package at.technikum.application.repository;

import at.technikum.application.model.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {

    private final List<User> userList =  new ArrayList<>();

    @Override
    public void save(User user) {
        userList.add(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userList.stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> findAllUsers() {
        return null;
    }

    @Override
    public boolean registerUser(String username, String password) {
        return true;
    }

    @Override
    public User updateUserByUsername(String username) {
        return null;
    }

    @Override
    public void deleteUserById(int id) {

    }
}
