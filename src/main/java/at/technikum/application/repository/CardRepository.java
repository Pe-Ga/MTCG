package at.technikum.application.repository;

import at.technikum.application.config.DbConnector;
import at.technikum.application.model.card.Card;
import at.technikum.application.model.card.ElementType;
import at.technikum.application.model.card.MonsterType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepository implements ICardRepository {


    private static final String SAVE_CARD = """
            INSERT INTO "Card" ("cardMonsterType", "cardDamage", "cardElementType", "cardOwner")
            VALUES (?, ?, ?, ?)
            """;

    private static final String GET_CARDS_READY_FOR_TRADING = """
            SELECT * from "Card"
            where "cardInTradeDeal" = true
            """;

    private static final String GET_ALL_CARDS = """
            SELECT * from "Card"
            """;

    private final DbConnector dataSource;

    public CardRepository(DbConnector dataSource) {
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
                    Card card = new Card();
                    card.setId(rs.getInt("cardId"));
                    card.setMonsterType(MonsterType.valueOf(rs.getString("cardMonsterType")));
                    card.setElementType(ElementType.valueOf(rs.getString("cardelementType")));
                    card.setOwnerId(rs.getInt("cardOwner"));
                    card.setBaseDamage(rs.getInt("cardDamage"));
                    tradingCardsList.add(card);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tradingCardsList;
    }

    @Override
    public List<Card> getCards() {
        List<Card> cardsList = new ArrayList<>();
        try (Connection tx = dataSource.getConnection())
        {
            try (PreparedStatement ps = tx.prepareStatement(GET_ALL_CARDS))
            {
                ps.execute();
                final ResultSet rs = ps.getResultSet();
                while (rs.next())
                {
                    Card card = new Card();
                    card.setId(rs.getInt("cardId"));
                    card.setMonsterType(MonsterType.valueOf(rs.getString("cardMonsterType")));
                    card.setElementType(ElementType.valueOf(rs.getString("cardelementType")));
                    card.setOwnerId(rs.getInt("cardOwner"));
                    card.setBaseDamage(rs.getInt("cardDamage"));
                    cardsList.add(card);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cardsList;
    }


}
