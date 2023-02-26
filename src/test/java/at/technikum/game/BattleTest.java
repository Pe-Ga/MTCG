package at.technikum.game;

import at.technikum.application.model.User;
import at.technikum.application.model.card.Card;
import at.technikum.application.model.card.ElementType;
import at.technikum.application.model.card.MonsterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static at.technikum.game.Battle.drawCard;
import static org.junit.jupiter.api.Assertions.*;


public class BattleTest
{

    private User user1;
    private User user2;
    private List<Card> deck1;
    private List<Card> deck2;
    private Battle battle;

    @BeforeEach
    void setUp() throws Exception
    {
        // create two users with their decks
        deck1 = new ArrayList<>(Arrays.asList(
                new Card(MonsterType.Spell,ElementType.Fire, 1),
                new Card(MonsterType.Spell,ElementType.Fire, 1),
                new Card(MonsterType.Spell,ElementType.Fire, 1),
                new Card(MonsterType.Spell,ElementType.Fire, 1)

        ));
        user1 = new User();
        user1.setRealName("Duncan");
        user1.setDeck(deck1);

        user1.setElo(100);
        user1.setWins(0);
        user1.setLosses(0);

        deck2 = new ArrayList<>(Arrays.asList(
                new Card(MonsterType.Spell,ElementType.Fire, 1),
                new Card(MonsterType.Spell,ElementType.Fire, 1),
                new Card(MonsterType.Spell,ElementType.Fire, 1),
                new Card(MonsterType.Spell,ElementType.Fire, 1)
        ));
        user2 = new User();
        user2.setRealName("Gurney");
        user2.setDeck(deck2);

        user2.setElo(100);
        user2.setWins(0);
        user2.setLosses(0);

        // create a battle with the two users
        battle = new Battle(user1, user2);
    }

    @Test
    void testDrawCard()
    {
        List<Card> deck1 = new LinkedList<Card>();
        deck1.add(new Card(MonsterType.Spell, ElementType.Fire, 1));
        deck1.add(new Card(MonsterType.Goblin, ElementType.Water, 1));
        deck1.add(new Card(MonsterType.Spell, ElementType.Normal, 1));
        deck1.add(new Card(MonsterType.Knight, ElementType.Fire, 1));

        assertNotNull(drawCard(deck1));
        assertNull(drawCard(null));
        assertNull(drawCard(new LinkedList<Card>()));
    }

    @Test
    void drawCardsFromDeck()
    {
        assertEquals(4, user1.getDeck().size());
        assertEquals(4, user2.getDeck().size());

        Card card1 = drawCard(user1.getDeck());
        Card card2 = drawCard(user2.getDeck());

        assertEquals(3, user1.getDeck().size());
        assertEquals(3, user2.getDeck().size());

        card1 = drawCard(user1.getDeck());
        card2 = drawCard(user2.getDeck());

        assertEquals(2, user1.getDeck().size());
        assertEquals(2, user2.getDeck().size());

        card1 = drawCard(user1.getDeck());
        card2 = drawCard(user2.getDeck());

        assertEquals(1, user1.getDeck().size());
        assertEquals(1, user2.getDeck().size());

        card1 = drawCard(user1.getDeck());
        card2 = drawCard(user2.getDeck());

        assertEquals(0, user1.getDeck().size());
        assertEquals(0, user2.getDeck().size());


    }

    @Test
    void drawCardsPlayOneRoundRestoreOriginalDeck()
    {
        //test deck
        Card card1 = new Card(MonsterType.Goblin, ElementType.Normal, 20);
        Card card2 = new Card(MonsterType.Goblin, ElementType.Normal, 10);

        List<Card> testDeck1 = new ArrayList<>();
        testDeck1.add(card1);

        List<Card> testDeck2 = new ArrayList<>();
        testDeck2.add(card2);

        user1.setDeck(testDeck1);
        user2.setDeck(testDeck2);

        user1.setUsername("PlayerA");
        user2.setUsername("PlayerB");

        assertEquals(1, user1.getDeck().size());
        assertEquals(1, user2.getDeck().size());

        Battle battle = new Battle(user1, user2);
        battle.fightBattle();

        assertEquals(1, user1.getDeck().size());
        assertEquals(1, user2.getDeck().size());
        assertTrue(true, String.valueOf(user1.getDeck().contains(card1)));
        assertTrue(true, String.valueOf(user2.getDeck().contains(card2)));
    }

    @Test
    void drawPlayerStatsRemainUnChanged()
    {
        Card card1 = new Card(MonsterType.Goblin, ElementType.Normal, 10);
        Card card2 = new Card(MonsterType.Goblin, ElementType.Normal, 10);

        List<Card> testDeck1 = new ArrayList<>();
        testDeck1.add(card1);

        List<Card> testDeck2 = new ArrayList<>();
        testDeck2.add(card2);

        user1.setUsername("PlayerA");
        user2.setUsername("PlayerB");
        user1.setDeck(testDeck1);
        user2.setDeck(testDeck2);
        battle.fightBattle();

        assertEquals(100, user1.getElo());
        assertEquals(100, user2.getElo());
        assertEquals(0, user1.getWins());
        assertEquals(0, user2.getWins());
        assertEquals(0, user1.getLosses());
        assertEquals(0, user2.getLosses());
    }

    @Test
    void playerBattleStatsUpdate()
    {
        Card card1 = new Card(MonsterType.Goblin, ElementType.Normal, 20);
        Card card2 = new Card(MonsterType.Goblin, ElementType.Normal, 10);

        List<Card> testDeck1 = new ArrayList<>();
        testDeck1.add(card1);

        List<Card> testDeck2 = new ArrayList<>();
        testDeck2.add(card2);

        user1.setUsername("PlayerA");
        user2.setUsername("PlayerB");
        user1.setDeck(testDeck1);
        user2.setDeck(testDeck2);
        battle.fightBattle();

        assertEquals(103, user1.getElo());
        assertEquals(95, user2.getElo());
        assertEquals(1, user1.getWins());
        assertEquals(0, user2.getWins());
        assertEquals(0, user1.getLosses());
        assertEquals(1, user2.getLosses());
    }

    @Test
    void testPlayerNameDecorationBasedOnElo()
    {
        user1.setUsername("Duncan");
        user1.setElo(100);
        assertEquals("Duncan", user1.getUsername());

        user1.setElo(104);
        assertEquals("_xxXDuncanXxx_", user1.toString());

        user1.setElo(115);
        assertEquals("-=##@Duncan@##=-", user1.toString());

        user1.setElo(131);
        assertEquals("<<||Duncan||>>", user1.toString());
    }


    @Test
    void testDrawCardRemoveFromDeck()
    {
        assertEquals(4, user1.getDeck().size());
        assertEquals(4, user2.getDeck().size());

         drawCard(user1.getDeck());
         drawCard(user2.getDeck());

        assertEquals(3, user1.getDeck().size());
        assertEquals(3, user2.getDeck().size());

        drawCard(user1.getDeck());
        drawCard(user2.getDeck());

        assertEquals(2, user1.getDeck().size());
        assertEquals(2, user2.getDeck().size());
    }

    @Test
    void testBattleLogContainsCorrectUserNames()
    {
        Card card1 = new Card(MonsterType.Goblin, ElementType.Normal, 20);
        Card card2 = new Card(MonsterType.Goblin, ElementType.Normal, 10);

        List<Card> testDeck1 = new ArrayList<>();
        testDeck1.add(card1);

        List<Card> testDeck2 = new ArrayList<>();
        testDeck2.add(card2);

        user1.setUsername("PlayerA");
        user2.setUsername("PlayerB");
        user1.setDeck(testDeck1);
        user2.setDeck(testDeck2);
        String battleReport = battle.fightBattle();

        String testBattleReport = """
                    %s    VS    %s
                    
                Round: 1
                 Card 1            | Base damage       | Effective Damage  | Card 2            | Base damage       | Effective Damage  |
                 NormalGoblin      | 20                | 20,00             | NormalGoblin      | 10                | 10,00             |
                 %s wins this round.
                                
                %s won the battle.""".formatted(user1.getUsername(), user2.getUsername(), user1.getUsername(), user1.getUsername());

       assertEquals(testBattleReport, battleReport);

    }

    @Test
    void specialCases()
    {
        Card card1 = new Card(MonsterType.Goblin, ElementType.Normal, 20);
        Card card2 = new Card(MonsterType.Dragon, ElementType.Normal, 10);

        List<Card> testDeck1 = new ArrayList<>();
        testDeck1.add(card1);

        List<Card> testDeck2 = new ArrayList<>();
        testDeck2.add(card2);

        user1.setUsername("PlayerA");
        user2.setUsername("PlayerB");
        user1.setDeck(testDeck1);
        user2.setDeck(testDeck2);
        String battleReport = battle.fightBattle();

        String testBattleReport = """
                    %s    VS    %s
                                
                Round: 1
                 Special case: NormalGoblin defeated by NormalDragon
                 %s wins this round.
                                
                %s won the battle.""".formatted(user1.getUsername(), user2.getUsername(), user2.getUsername(), user2.getUsername());

        assertEquals(testBattleReport, battleReport);
    }

    @Test
    void specialCaseSet()
    {
        SpecialCaseSet specialCases = Battle.buildMtcgSpecialCases();

        Card goblin     = new Card(MonsterType.Goblin, ElementType.Normal, 0);
        Card dragon     = new Card(MonsterType.Dragon, ElementType.Normal, 0);
        Card fireElve   = new Card(MonsterType.Elve  , ElementType.Fire  , 0);
        Card wizard     = new Card(MonsterType.Wizard, ElementType.Normal, 0);
        Card orc        = new Card(MonsterType.Orc   , ElementType.Normal, 0);
        Card knight     = new Card(MonsterType.Knight, ElementType.Normal, 0);
        Card waterSpell = new Card(MonsterType.Spell , ElementType.Water , 0);
        Card kraken     = new Card(MonsterType.Kraken, ElementType.Normal, 0);
        Card spell      = new Card(MonsterType.Spell , ElementType.Normal, 0);
        Card elve       = new Card(MonsterType.Elve  , ElementType.Normal, 0);

        assertEquals(SpecialCaseResult.CARD2_WINS,
                specialCases.evaluate(goblin, dragon)
        );

        assertEquals(SpecialCaseResult.CARD2_WINS,
                specialCases.evaluate(dragon, fireElve)
        );

        assertEquals(SpecialCaseResult.CARD1_WINS,
                specialCases.evaluate(dragon, goblin)
        );

        assertEquals(SpecialCaseResult.CARD1_WINS,
                specialCases.evaluate(wizard, orc)
        );

        assertEquals(SpecialCaseResult.CARD2_WINS,
                specialCases.evaluate(orc, wizard)
        );

        assertEquals(SpecialCaseResult.CARD2_WINS,
                specialCases.evaluate(knight, waterSpell)
        );

        assertEquals(SpecialCaseResult.CARD1_WINS,
                specialCases.evaluate(fireElve, dragon)
        );

        assertEquals(SpecialCaseResult.CARD1_WINS,
                specialCases.evaluate(kraken, spell)
        );

        assertEquals(SpecialCaseResult.CARD1_WINS,
                specialCases.evaluate(waterSpell, knight)
        );

        assertEquals(SpecialCaseResult.CARD2_WINS,
                specialCases.evaluate(waterSpell, kraken)
        );

        assertEquals(SpecialCaseResult.CARD2_WINS,
                specialCases.evaluate(spell, kraken)
        );

        assertEquals(SpecialCaseResult.NO_SPECIAL_CASE,
                specialCases.evaluate(goblin, knight)
        );

        assertEquals(SpecialCaseResult.NO_SPECIAL_CASE,
                specialCases.evaluate(fireElve, kraken)
        );

        assertEquals(SpecialCaseResult.NO_SPECIAL_CASE,
                specialCases.evaluate(elve, dragon)
        );

        assertEquals(SpecialCaseResult.NO_SPECIAL_CASE,
                specialCases.evaluate(dragon, elve)
        );
    }
}
