package at.technikum.game;

import at.technikum.card.Card;
import at.technikum.card.ElementType;
import at.technikum.card.MonsterType;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GameTest
{

    @Test
    void testDrawCard()
    {
        List<Card> deck1 = new LinkedList<Card>();
        deck1.add(new Card(MonsterType.Spell, ElementType.Fire, 1));
        deck1.add(new Card(MonsterType.Goblin, ElementType.Water, 1));
        deck1.add(new Card(MonsterType.Spell, ElementType.Normal, 1));
        deck1.add(new Card(MonsterType.Knight, ElementType.Fire, 1));

        assertNotNull(Game.drawCard(deck1));
        assertNull(Game.drawCard(null));
        assertNull(Game.drawCard(new LinkedList<Card>()));
    }
}
