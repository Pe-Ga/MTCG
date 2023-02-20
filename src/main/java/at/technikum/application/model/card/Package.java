package at.technikum.application.model.card;

public class Package {

    private Card card;

    public Package () {};

    public Package(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
