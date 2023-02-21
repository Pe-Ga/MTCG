package at.technikum.application.repository;


import at.technikum.application.model.card.Card;

import java.util.List;

public interface ICardRepository {

    void saveCard(Card card, int userId);
    List<Card> getCardsAllowedToTrade();

    List<Card> getCards();
}
