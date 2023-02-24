package at.technikum.game;

import at.technikum.application.model.User;
import at.technikum.application.model.card.Card;

import java.util.*;

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

    public String determineRoundsWinner()
    {
        Card card1 = drawCard(this.user1.getDeck()); // TODO: cards in some tests were null!! check!
        Card card2 = drawCard(this.user2.getDeck());

        String battleReport = card1.toString() + " vs " + card2.toString() + " => ";

        // TODO check special case

        float roundResult = card1.calculatedDamage(card2) - card2.calculatedDamage(card1);

        User winner = null;
        if(roundResult < 0)
            winner = this.user2;
        else if (roundResult > 0)
            winner = this.user1;

        if(winner!=null)
        {
            winner.getDeck().add(card1);
            winner.getDeck().add(card2);
            battleReport += winner + " wins.";
        }
        else
        {
            this.user1.getDeck().add(card1);
            this.user2.getDeck().add(card2);
            battleReport += "It's a Draw.";
        }

        return battleReport;
    }

    public void addCardToDeck (Card card, User user)
    {
        user.getDeck().add(card);
    }

    public String fightBattle()
    {
        String battleReport = "";
        int roundCounter = 0;

        //List<Card> originalDeck1 = new ArrayList<Card>();
        //List<Card> originalDeck2 = new ArrayList<Card>();

        ///Collections.copy(originalDeck1, user1.getDeck());
        //Collections.copy(originalDeck2, user2.getDeck());

        do
        {
            roundCounter++;
            battleReport += (determineRoundsWinner() + "\n");
        } while (roundCounter == 100 || user1.getDeck().isEmpty() || user2.getDeck().isEmpty());

        if (user1.getDeck().isEmpty())
        {
            user1.setStatsLoss();
            user2.setStatsWin();
            battleReport += user2.getUsername() + " won the battle.";
        }
        else if (user2.getDeck().isEmpty())
        {
            user2.setStatsLoss();
            user1.setStatsWin();
            battleReport += user1.getUsername() + " won the battle.";
        }
        else
        {
            battleReport += "Draw.";
        }

        //user1.setDeck(originalDeck1);
        //user2.setDeck(originalDeck2);

        return battleReport;
    }
}
