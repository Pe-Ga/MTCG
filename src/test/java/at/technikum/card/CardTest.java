package at.technikum.card;

import at.technikum.application.model.card.Card;
import at.technikum.application.model.card.ElementType;
import at.technikum.application.model.card.MonsterType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest
{

    @Test
    void testIsMonster()
    {
        for(MonsterType type : MonsterType.values())
        {
           Card monsterCard = new Card(type, ElementType.Normal,10);
            if(type == MonsterType.Spell)
                assertFalse(monsterCard.isMonster());
            else
                assertTrue(monsterCard.isMonster());
        }

        MonsterType nullType = null;
        Card nullCard = new Card(nullType,ElementType.Fire,10);
        assertFalse(nullCard.isMonster());
    }

    @Test
    void testCalculatedDamage()
    {

        Card nullCard     = null;
        Card monsterCard1 = new Card(MonsterType.Goblin, ElementType.Fire,10);
        Card monsterCard2 = new Card(MonsterType.Knight, ElementType.Fire,20);
        Card fireCard     = new Card(MonsterType.Spell , ElementType.Fire,20);
        Card waterCard    = new Card(MonsterType.Spell , ElementType.Water,20);
        Card normalCard   = new Card(MonsterType.Spell , ElementType.Normal,20);

        float halvedDamage  = 0.5f;
        float normalDamage  = 1.0f;
        float doubledDamage = 2.0f;
        float zeroDamage    =   0f;

        // Null
        assertEquals(monsterCard1.calculatedDamage(nullCard    ), zeroDamage   * monsterCard1.getBaseDamage());

        // Monster VS Monster
        assertEquals(monsterCard1.calculatedDamage(monsterCard2), normalDamage * monsterCard1.getBaseDamage());

        //Fire
        assertEquals(fireCard.calculatedDamage(normalCard), doubledDamage * fireCard.getBaseDamage());
        assertEquals(fireCard.calculatedDamage(waterCard ), halvedDamage  * fireCard.getBaseDamage());
        assertEquals(fireCard.calculatedDamage(fireCard  ), normalDamage  * fireCard.getBaseDamage());

        //Water
        assertEquals(waterCard.calculatedDamage(fireCard  ), doubledDamage * waterCard.getBaseDamage());
        assertEquals(waterCard.calculatedDamage(waterCard ), normalDamage  * waterCard.getBaseDamage());
        assertEquals(waterCard.calculatedDamage(normalCard), halvedDamage  * waterCard.getBaseDamage());

        //Normal
        assertEquals(normalCard.calculatedDamage(waterCard ), doubledDamage * normalCard.getBaseDamage());
        assertEquals(normalCard.calculatedDamage(fireCard  ), halvedDamage  * normalCard.getBaseDamage());
        assertEquals(normalCard.calculatedDamage(normalCard), normalDamage  * normalCard.getBaseDamage());

    }

    @Test
    public void testCalculatedDamageForNullCard()
    {
        Card card1 = new Card(MonsterType.Dragon, ElementType.Fire, 10);
        float expectedDamage = 0;
        float actualDamage = card1.calculatedDamage(null);
        assertEquals(expectedDamage, actualDamage, 0.0);
    }

    @Test
    public void testCalculatedDamageForSameElementType()
    {
        Card card1 = new Card(MonsterType.Orc, ElementType.Water, 10);
        Card card2 = new Card(MonsterType.Goblin, ElementType.Water, 5);
        float expectedDamage = 10;
        float actualDamage = card1.calculatedDamage(card2);
        assertEquals(expectedDamage, actualDamage, 0.0);
    }

    @Test
    public void testCalculatedDamageForDifferentElementTypes()
    {
        Card card1 = new Card(MonsterType.Elve, ElementType.Fire, 15);
        Card card2 = new Card(MonsterType.Knight, ElementType.Water, 10);
        float expectedDamage = 15;
        float actualDamage = card1.calculatedDamage(card2);
        assertEquals(expectedDamage, actualDamage, 0.0);
    }

    @Test
    public void testIsSpecialCase()
    {
        Card card1 = new Card(MonsterType.Dragon, ElementType.Fire, 10);
        Card card2 = new Card(MonsterType.Goblin, ElementType.Water, 5);
        boolean expectedResult = true;
        boolean actualResult = card1.isSpecialCase(card2);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testIsNotSpecialCase()
    {
        Card card1 = new Card(MonsterType.Dragon, ElementType.Fire, 10);
        Card card2 = new Card(MonsterType.Orc, ElementType.Normal, 8);
        boolean expectedResult = false;
        boolean actualResult = card1.isSpecialCase(card2);
        assertEquals(expectedResult, actualResult);
    }


    @Test
    public void testIsSpecialCaseForTwoCards()
    {
        Card card1 = new Card(MonsterType.Wizard, ElementType.Normal, 10);
        Card card2 = new Card(MonsterType.Orc, ElementType.Normal, 5);
        boolean expectedResult = true;
        boolean actualResult = Card.isSpecialCase(card1, card2);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testIsNotSpecialCaseForTwoCards()
    {
        Card card1 = new Card(MonsterType.Elve, ElementType.Fire, 12);
        Card card2 = new Card(MonsterType.Kraken, ElementType.Normal, 7);
        boolean expectedResult = false;
        boolean actualResult = Card.isSpecialCase(card1, card2);
        assertEquals(expectedResult, actualResult);
    }

}
