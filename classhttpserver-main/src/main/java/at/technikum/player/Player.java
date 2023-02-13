package at.technikum.player;
import at.technikum.application.model.card.Card;
import java.util.List;

public class Player
{

    private String name;
    private List<Card> deck;
    private List<Card> collection;

    public Player(){}

    public Player(String name, List<Card> deck, List<Card> collection)
    {
        this.name = name;
        this.deck = deck;
        this.collection = collection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
