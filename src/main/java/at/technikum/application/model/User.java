package at.technikum.application.model;

import at.technikum.application.model.card.Card;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class User {
    
    private int userId;
    private String username;
    private String password;

    private String realName;
    private String bio;
    private String image;

    private int elo;

    private int wins;

    private int losses;

    private List<Card> deck;

    private List<Card> collection;

    private String userToken;

    private Instant userTokenExpiration;

    public User() {
        this.username = username;
        this.password = password;
    }

    public User(int userId,String username, String password, String realName, String bio, String image,
                int elo, int wins, int losses, List<Card> deck, List<Card> collection) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.bio = bio;
        this.image = image;
        this.elo = elo;
        this.wins = wins;
        this.losses = losses;
        this.deck = deck;
        this.collection = collection;
    }

    public User(String username, String password, String bio, String image) {
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Card> getCollection() {
        return collection;
    }

    public void setCollection(List<Card> collection) {
        this.collection = collection;
    }

    public String getUserToken() {
        return userToken;
    }

    public Instant getUserTokenExpiration() {
        return userTokenExpiration;
    }

    public void setUserTokenExpiration(Instant userTokenExpiration) {
        this.userTokenExpiration = userTokenExpiration;
    }

    public boolean userNameExists()
    {
        return this.username != null;
    }

    public void setUserToken(String token) {
        this.userToken = token;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
/*
    @Override
    public String toString()
    {
        return this.getUsername();
    }
*/
}
