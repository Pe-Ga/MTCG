package at.technikum.game;

import at.technikum.application.model.User;
import at.technikum.application.model.card.Card;

import java.util.*;

import static at.technikum.application.model.card.Card.specialCase;

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
        Card card1 = drawCard(this.user1.getDeck());
        Card card2 = drawCard(this.user2.getDeck());

        if (card1 == null || card2 == null)
            return user1 + " vs. " + user2 + "\nBattle aborted. Reason: No cards in a user's deck.";

        System.out.println(card1.isSpecialCase(card2) || card2.isSpecialCase(card1));

        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append(String.format(" %-18s| %-18s| %-18s| %-18s| %-18s| %-18s|\n", "Card 1", "Base damage", "Effective Damage", "Card 2", "Base damage", "Effective Damage"));
        tableBuilder.append(String.format(" %-18s| %-18s| %-18.2f| %-18s| %-18s| %-18.2f|\n", card1.onlyNameToString(), card1.getBaseDamage(), card1.calculatedDamage(card2), card2.onlyNameToString(), card2.getBaseDamage(), card2.calculatedDamage(card1)));

        User winner = null;

        // TODO check special case
        if (specialCase(card1, card2))
        {
            System.out.println("We got ourselves a special case");
            if (card2.isSpecialCase(card1))
            {
                winner = this.user2;
                winner.getDeck().add(card1);
                winner.getDeck().add(card2);
                tableBuilder.append(" Special Case").append(" " + winner.getUsername() + " wins this round.\n");
            }
            else if (card1.isSpecialCase(card2))
            {
                winner = this.user1;
                winner.getDeck().add(card1);
                winner.getDeck().add(card2);
                tableBuilder.append(" Special Case").append(" " + winner.getUsername() + " wins this round.\n");
            }
            return tableBuilder.toString();
        }

        float roundResult = card1.calculatedDamage(card2) - card2.calculatedDamage(card1);

        if(roundResult < 0)
            winner = this.user2;
        else if (roundResult > 0)
            winner = this.user1;

        if(winner!=null)
        {
            winner.getDeck().add(card1);
            winner.getDeck().add(card2);
            tableBuilder.append(" " + winner.getUsername() + " wins this round.\n");
        }
        else
        {
            this.user1.getDeck().add(card1);
            this.user2.getDeck().add(card2);
            tableBuilder.append(" It's a Draw.");
        }

        return tableBuilder.toString();
    }

    public void addCardToDeck (Card card, User user)
    {
        user.getDeck().add(card);
    }

    public String fightBattle()
    {
        StringBuilder battleReport = new StringBuilder();
        int roundCounter = 0;

        List<Card> originalDeck1 = new ArrayList<>();
        List<Card> originalDeck2 = new ArrayList<>();

        originalDeck1.addAll(user1.getDeck());
        originalDeck2.addAll(user2.getDeck());

        battleReport.append("    ").append(user1).append("    VS    ").append(user2).append("\n");
        do
        {
            roundCounter++;
            battleReport.append("Round: " + roundCounter + "\n").append(determineRoundsWinner()).append("\n");
        } while ((!user1.getDeck().isEmpty() && !user2.getDeck().isEmpty()) && roundCounter <= 100);

        if (user1.getDeck().isEmpty())
        {
            user1.setStatsLoss();
            user2.setStatsWin();
            battleReport.append(user2 + " won the battle.");
        }
        else if (user2.getDeck().isEmpty())
        {
            user2.setStatsLoss();
            user1.setStatsWin();
            battleReport.append(user1 + " won the battle.");
        }
        else
        {
            battleReport.append("Draw.");
        }

        user1.setDeck(originalDeck1);
        user2.setDeck(originalDeck2);

        System.out.println(battleReport);

        return battleReport.toString();
    }
}
