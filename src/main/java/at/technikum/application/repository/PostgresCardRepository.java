package at.technikum.application.repository;

import at.technikum.application.config.DbConnector;
import at.technikum.application.model.User;
import at.technikum.application.model.card.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgresCardRepository implements CardRepository{


    private static final String SAVE_CARD = """
            INSERT INTO "Card" ("cardMonsterType", "cardDamage", "cardElementType", "cardOwner")
            VALUES (?, ?, ?, ?)
            """;

    private final DbConnector dataSource;

    public PostgresCardRepository(DbConnector dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveCard(Card card, int userId) {
        try (Connection tx = dataSource.getConnection())
        {
            try (PreparedStatement ps = tx.prepareStatement(SAVE_CARD))
            {
                ps.setString(1, String.valueOf(card.getMonsterType()));
                ps.setInt(2, card.getBaseDamage());
                ps.setString(3, String.valueOf(card.getElementType()));
                ps.setInt(4, userId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
