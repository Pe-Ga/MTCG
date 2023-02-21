package at.technikum.application.repository;


import at.technikum.application.model.card.Card;

import java.util.List;

public interface CardRepository {

    void saveCard(Card card, int userId);
    List<Card> getCardsAllowedToTrade();
}
