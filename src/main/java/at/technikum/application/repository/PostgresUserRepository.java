package at.technikum.application.repository;

import at.technikum.application.config.DbConnector;
import at.technikum.application.model.User;
import at.technikum.application.model.card.Card;
import at.technikum.application.model.card.ElementType;
import at.technikum.application.model.card.MonsterType;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static at.technikum.application.util.AccessTokenGenerator.generateAccessToken;

public class PostgresUserRepository implements UserRepository {
    private static final String FIND_BY_USERNAME = """
            SELECT * from "User" WHERE "userName" = ?
            """;

    private static final String GET_COLLECTION = """
            SELECT * from "Card" WHERE "cardOwner" = ?
            """;

    private static final String FIND_CARD_BELONGING_TO_DECK = """
            SELECT * from "Card" Where "cardId" = ? or "cardId" = ? or "cardId" = ? or "cardId" = ?;
            """;

    private static final String GET_DECK = """
            SELECT * from "Deck" Where "ownerId" = ?;
            """;

    private static final String CHECK_USER_CREDENTIALS = """
            SELECT userName, userPassword FROM "User" WHERE "userName" = ? AND "userPassword" = ?
            """;

    private static final String UPDATE_USER = """
            UPDATE "User"
            SET "userName" = ?,
            "userPassword" = ?,
            "userBio" = ?,
            "userImage" = ?,
            "userRealname" = ?,
            "elo" = ?,
            "wins" = ?,
            "losses" = ?,
            "userToken" = ?,
            "userTokenExpiration" = ?,
            "userCoins" = ?
            Where "userId" = ?
            """;

    private static final String SETUP_TABLE = """
            create table public."User"
            (
                "userId"              serial
                    constraint "User_pk"
                        primary key,
                "userName"            varchar                                           not null,
                "userPassword"        varchar                                           not null,
                "userBio"             varchar,
                "userImage"           varchar,
                "userRealname"        varchar default 'userRealname'::character varying not null,
                elo                   integer default 100                               not null,
                wins                  integer default 0                                 not null,
                losses                integer default 0                                 not null,
                "userToken"           varchar(255),
                "userTokenExpiration" timestamp
            );
                        
            alter table public."User"
                owner to swen1user;
                        
                        
            """;

    private static final String FIND_ALL_USERS = """
            SELECT * from "User" 
            """;

    private static final String REGISTER_USER = """
            INSERT INTO "User"
            ("userName", "userPassword", "userToken", "userTokenExpiration")
            VALUES (?, ?, ?, ?)
            """;

    private static final String UPDATE_ACCESS_TOKEN = """
        UPDATE "User" SET "userToken" = ?, "userTokenExpiration" = ? WHERE "userName" = ?
        """;

    private static final String FIND_BY_TOKEN = """
        SELECT * from "User" WHERE userToken = ?
        """;

    private final DbConnector dataSource;

    // TODO
    public PostgresUserRepository(DbConnector dataSource) {
        this.dataSource = dataSource;
        try (PreparedStatement ps = dataSource.getConnection()
                .prepareStatement("SELECT * FROM \"User\"")){
            ps.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to setup up table", e);
        }
    }

    @Override
    public User findUser(String username) throws SQLException {
        User user = null;
        List <Card> collection = new ArrayList<Card>();
        List <Card> deck = new ArrayList<Card>();
        try ( Connection tx = dataSource.getConnection()) {
            try ( PreparedStatement ps = tx.prepareStatement(FIND_BY_USERNAME))
            {
                ps.setString(1, username);
                ps.execute();
                final ResultSet rs = ps.getResultSet();
                if(rs.next())
                {
                    user = new User();
                    user.setUserId((rs.getInt("userId")));
                    user.setUsername(rs.getString("userName"));
                    user.setPassword(rs.getString("userPassword"));
                    user.setBio(rs.getString("userBio"));
                    user.setImage(rs.getString("userImage"));
                    user.setRealName(rs.getString("userRealname"));
                    user.setElo(Integer.parseInt(rs.getString("elo")));
                    user.setWins(Integer.parseInt(rs.getString("wins")));
                    user.setLosses(Integer.parseInt(rs.getString("losses")));
                    user.setUserToken(rs.getString("userToken"));
                    user.setUserTokenExpiration(rs.getTimestamp("userTokenExpiration").toInstant());
                    user.setCoins(rs.getInt("userCoins"));
                }
            }
            if (user != null)
            {
                try (PreparedStatement psGetDeck = tx.prepareStatement(GET_DECK)) {
                    psGetDeck.setInt(1, user.getUserId());
                    psGetDeck.execute();
                    final ResultSet rs = psGetDeck.getResultSet();
                    while (rs.next()) {
                        try (PreparedStatement pstmt = tx.prepareStatement(FIND_CARD_BELONGING_TO_DECK)) {
                            pstmt.setInt(1, rs.getInt("card1"));
                            pstmt.setInt(2, rs.getInt("card2"));
                            pstmt.setInt(3, rs.getInt("card3"));
                            pstmt.setInt(4, rs.getInt("card4"));
                            pstmt.execute();
                            final ResultSet resultSet = pstmt.getResultSet();
                            while (resultSet.next()) {
                                Card card = new Card();
                                card.setMonsterType(MonsterType.valueOf(resultSet.getString("cardMonsterType")));
                                card.setElementType(ElementType.valueOf(resultSet.getString("cardelementType")));
                                card.setBaseDamage(resultSet.getInt("cardDamage"));
                                deck.add(card);
                            }
                        }
                        user.setDeck(deck);
                    }
                }
            }
            try (PreparedStatement ps = tx.prepareStatement(GET_COLLECTION))
            {
                if(user != null)
                {
                    ps.setInt(1, user.getUserId());
                    ps.execute();
                    final ResultSet rs_collection = ps.getResultSet();
                    while(rs_collection.next())
                    {
                        Card card = new Card();
                        card.setMonsterType(MonsterType.valueOf(rs_collection.getString("cardMonsterType")));
                        card.setElementType(ElementType.valueOf(rs_collection.getString("cardelementType")));
                        card.setBaseDamage(rs_collection.getInt("cardDamage"));
                        collection.add(card);
                    }
                    user.setCollection(collection);
                }
            }
        }
        catch (SQLException e)
        {
            throw new IllegalStateException("DB query failed", e);
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() throws SQLException {
        User user = new User();
        List<User> userList = new ArrayList<>();
        try ( Connection tx = dataSource.getConnection()) {
            try ( PreparedStatement ps = tx.prepareStatement(FIND_ALL_USERS)) {
                ps.execute();
                final ResultSet rs = ps.getResultSet();
                while (rs.next()) {
                    user.setUsername((rs.getString("userName")));
                    userList.add(user);
                }
            }
        }
        return userList;
    }

    @Override
    public boolean registerUser(User user) throws SQLException {
        try (Connection tx = dataSource.getConnection()) {
            try (PreparedStatement ps = tx.prepareStatement(REGISTER_USER)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getUserToken());
                ps.setTimestamp(4, Timestamp.from(user.getUserTokenExpiration()));
                ps.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    public User findUserByToken(String token)  throws SQLException{
        User user = null;
        try (Connection tx = dataSource.getConnection()) {
            try (PreparedStatement ps = tx.prepareStatement(FIND_BY_TOKEN)) {
                ps.setString(1, token);
                ps.execute();
                final ResultSet rs = ps.getResultSet();
                while (rs.next()) {
                    user = new User();
                    user.setUsername(rs.getString("userName"));
                    user.setBio(rs.getString("userBio"));
                    user.setImage(rs.getString("userImage"));
                    user.setUserToken(rs.getString("userToken"));         //set expiration date to 15mins from now
                   // user.setAccessTokenExpiration(rs.getTimestamp("userTokenExpiration").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {

        try (Connection tx = dataSource.getConnection())
        {
            try (PreparedStatement ps = tx.prepareStatement(UPDATE_USER)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getBio());
                ps.setString(4, user.getImage());
                ps.setString(5, user.getRealName());
                ps.setInt(6, user.getElo());
                ps.setInt(7, user.getWins());
                ps.setInt(8, user.getLosses());
                ps.setString(9, user.getUserToken());
                ps.setTimestamp(10, Timestamp.from(user.getUserTokenExpiration()));
                ps.setInt(11, user.getUserId());
                ps.setInt(12,user.getCoins());
                ps.execute();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean loginUser(String username, String password)
    {
        if (this.loginIsValid(username, password)) // isValid
        {
            String userToken = generateAccessToken();
            Instant expiration = Instant.now().plus(Duration.ofMinutes(15));
            var user = new User();
            try ( Connection tx = dataSource.getConnection()) {
                try ( PreparedStatement ps = tx.prepareStatement(UPDATE_ACCESS_TOKEN)) {
                    ps.setString(1, userToken);
                    ps.setTimestamp(2, Timestamp.from(expiration));
                    ps.setString(3, username);
                    ps.execute();
                    final ResultSet rs = ps.getResultSet();
                    //TODO rs
                }
            }
            catch (SQLException e)
            {
                throw new IllegalStateException("DB query failed", e);
            }
            return false;

        }
        return true;
    }

    @Override
    public boolean loginIsValid(String username, String userpassword)
    {
        User user = new User();
        try ( Connection tx = dataSource.getConnection()) {
            try ( PreparedStatement ps = tx.prepareStatement(CHECK_USER_CREDENTIALS)) {
                ps.setString(1, username);
                ps.setString(2, userpassword);
                ps.execute();
                final ResultSet rs = ps.getResultSet();
                while (rs.next()) {
                    user.setUsername(rs.getString("userName"));
                    user.setUsername(rs.getString("userPassword"));
                }

                if (username.equals(user.getUsername()) && userpassword.equals(user.getPassword()))
                {
                    return true;
                }
            }
        }
        catch (SQLException e)
        {
            throw new IllegalStateException("DB query failed", e);
        }
        return false;
    }

/*
    @Override
    public boolean userExists (String username)
    {
        User user = new User();
        try (Connection tx = dataSource.getConnection())
        {
            try (PreparedStatement ps = tx.prepareStatement())
        }
    }
*/

    @Override
    public void deleteUserById(int id) {

    }

    @Override
    public void save(User user) {

    }



}
