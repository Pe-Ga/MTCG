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



}
