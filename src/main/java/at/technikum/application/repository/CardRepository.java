package at.technikum.application.repository;

import at.technikum.application.model.User;
import at.technikum.application.model.card.Card;

public interface CardRepository {

    void saveCard(Card card, int userId);
}
