package at.technikum.game;

import at.technikum.card.Card;
import at.technikum.card.MonsterType;
import at.technikum.player.Player;

import java.util.List;
import java.util.Random;


public class Game
{
    private Player player1, player2;

    public Game(Player player1, Player player2)
    {
        this.player1 = player1;
        this.player2 = player2;
    }

    public Player getPlayer1()
    {
        return player1;
    }

    public void setPlayer1(Player player1)
    {
        this.player1 = player1;
    }

    public Player getPlayer2()
    {
        return player2;
    }

    public void setPlayer2(Player player2)
    {
        this.player2 = player2;
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

    public Card foo(List<Card> deck1, List<Card> deck2)                                     //calculates which card wins
    {

        int calcDmgCard1;
        int calcDmgCard2;

        Card card1 = drawCard(deck1);
        Card card2 = drawCard(deck2);

       return card1.calculatedDamage(card2) > card2.calculatedDamage(card1) ? card2 : card1;

    }

    //TODO add card to deck

    public void addCardToDeck (Card card, Player player)
    {
        player.getDeck().add(card);
    }

}
