package at.technikum.application.repository;

import at.technikum.application.config.DbConnector;
import at.technikum.application.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresUserRepository implements UserRepository{
    private static final String FIND_BY_USERNAME = """
            SELECT * from User
            """;

    private static final String SETUP_TABLE = """
            create table public."User"
            (
                "userId"       serial
                    constraint "User_pk"
                        primary key,
                "userName"     varchar                                           not null,
                "userPassword" varchar                                           not null,
                "userBio"      varchar,
                "userImage"    varchar,
                "userRealname" varchar default 'userRealname'::character varying not null,
                elo            integer default 100                               not null,
                wins           integer default 0                                 not null,
                losses         integer default 0                                 not null,
                deck           json,
                collection     json
            );
            """;

    private static final String FIND_ALL_USERS = """
            SELECT * from User
            """;

    private static final String REGISTER_USER = """
            INSERT INTO User(userName, userPassword) + VALUES(?, ?)
            """;

    private final DbConnector dataSource;

    public PostgresUserRepository(DbConnector dataSource) {
        this.dataSource = dataSource;
        try (PreparedStatement ps = dataSource.getConnection()
                .prepareStatement(SETUP_TABLE)){
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserByUsername(String username) throws SQLException {
        User user = new User();
        try ( Connection tx = dataSource.getConnection()) {
            try ( PreparedStatement ps = tx.prepareStatement(FIND_BY_USERNAME)) {
                ps.execute();
                final ResultSet rs = ps.getResultSet();
                while (rs.next()) {
                    user.setUsername(rs.getString("userName"));
                    user.setBio(rs.getString("userBio"));
                    user.setImage(rs.getString("userImage"));
                }
            }
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        try ( Connection tx = dataSource.getConnection()) {
            try ( PreparedStatement ps = tx.prepareStatement(FIND_ALL_USERS)) {
                ps.execute();
                final ResultSet rs = ps.getResultSet();
                while (rs.next()) {
                    //TODO add username to list

                }
            }
        }
        return userList;
    }

    @Override
    public boolean registerUser(String username, String password) throws SQLException {
        try ( Connection tx = dataSource.getConnection()) {
            try ( PreparedStatement ps = tx.prepareStatement(REGISTER_USER)) {
                ps.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
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
