package at.technikum.game;

import at.technikum.application.model.User;
import at.technikum.application.model.card.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle
{
    private User user1, user2;



    public Battle(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;

    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    private static int getRandomIndex(List<Card> deck)
    {
        if(deck != null && !deck.isEmpty())
        {
            int listLength = deck.size();
            Random randomGenerator = new Random();
            return randomGenerator.nextInt(listLength);
        }
        else
        {
            return -1;
        }
    }

    public static Card drawCard(List<Card> deck)
    {
        return (deck == null || deck.isEmpty()) ? null : deck.remove(getRandomIndex(deck));
    }

    public String determineRoundsWinner(List<Card> deck1, List<Card> deck2)
    {
       String battleReport = null;
       Card card1 = drawCard(deck1);
       Card card2 = drawCard(deck2);



        // TODO check special case
        if (card1.calculatedDamage(card2) > card2.calculatedDamage(card1))
        {
            battleReport += card1.toString() + " vs " + card2.toString() + "\n" + card1.onlyNameToString() + "defeats" + card2.onlyNameToString() + "\n";
            user1.getDeck().add(card1);
            user1.getDeck().add(card2);
        }
        else if (card1.calculatedDamage(card2) < card2.calculatedDamage(card1))
        {
            battleReport += card1.toString() + " vs " + card2.toString() + "\n" + card2.onlyNameToString() + "defeats" + card1.onlyNameToString() + "\n";
            user2.getDeck().add(card2);
            user2.getDeck().add(card1);
        }
        return battleReport;
    }

    public void addCardToDeck (Card card, User user)
    {
        user.getDeck().add(card);
    }

    public List<String> fightBattle()
    {
        List<String> battleLog = new ArrayList<>();
        int roundCounter = 0;

        do
        {
            roundCounter++;
            battleLog.add(determineRoundsWinner(user1.getDeck(), user2.getDeck()));
        } while (roundCounter == 100 || user1.getDeck().isEmpty() || user2.getDeck().isEmpty());

        return battleLog;
    }
}
