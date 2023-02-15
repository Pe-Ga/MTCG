package at.technikum.application.repository;

import at.technikum.application.model.card.Card;

public interface CardRepository {

    Card findCardById(int cardId);
}
