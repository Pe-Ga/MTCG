package at.technikum.application.repository;

import at.technikum.application.config.DbConnector;
import at.technikum.application.model.User;
import at.technikum.application.model.card.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresCardRepository implements CardRepository{


    private static final String SAVE_CARD = """
            INSERT INTO "Card" ("cardMonsterType", "cardDamage", "cardElementType", "cardOwner")
            VALUES (?, ?, ?, ?)
            """;

    private static final String GET_CARDS_READY_FOR_TRADING = """
            SELECT * from "Card"
            where "cardInTradeDeal" = true
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
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Card> getCardsAllowedToTrade() {
        List<Card> tradingCardsList = new ArrayList<>();
        try (Connection tx = dataSource.getConnection())
        {
            try (PreparedStatement ps = tx.prepareStatement(GET_CARDS_READY_FOR_TRADING))
            {
                ps.execute();
                final ResultSet rs = ps.getResultSet();
                while (rs.next())
                {

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


}
