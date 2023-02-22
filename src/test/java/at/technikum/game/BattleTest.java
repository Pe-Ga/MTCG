package at.technikum.game;

import at.technikum.application.model.card.Card;
import at.technikum.application.model.card.ElementType;
import at.technikum.application.model.card.MonsterType;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BattleTest
{

    @Test
    void testDrawCard()
    {
        List<Card> deck1 = new LinkedList<Card>();
        deck1.add(new Card(MonsterType.Spell, ElementType.Fire, 1));
        deck1.add(new Card(MonsterType.Goblin, ElementType.Water, 1));
        deck1.add(new Card(MonsterType.Spell, ElementType.Normal, 1));
        deck1.add(new Card(MonsterType.Knight, ElementType.Fire, 1));

        assertNotNull(Battle.drawCard(deck1));
        assertNull(Battle.drawCard(null));
        assertNull(Battle.drawCard(new LinkedList<Card>()));
    }
}
