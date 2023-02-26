package at.technikum.user;

import at.technikum.application.model.User;
import at.technikum.application.model.card.Card;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest
{

    @BeforeEach
    void setUp() throws Exception
    {
        List<Card> deck1 = new ArrayList<>();
        List<Card> collection1 = new ArrayList<>();

        User testUser = new User(1,"Tester",
                                        "Tester123",
                                        "Real Tester",
                                        "MonsterTrader",
                                        "<@8)",
                                        100,
                                        0,
                                        0,
                                        deck1,
                                        collection1,
                                        20);




    }

    @Test
    void eloChangeUserStats() throws Exception {

        User testUser = new User();
        testUser.setUsername("Tester");
        testUser.setElo(100);
        testUser.setWins(0);
        testUser.setLosses(0);

        assertEquals("Tester", testUser.getUsername());

        testUser.setStatsWin();

        assertEquals(103, testUser.getElo());
        assertEquals(1, testUser.getWins());
        assertEquals(0, testUser.getLosses());

        assertEquals("Tester", testUser.toString());

        testUser.setStatsWin();

        assertEquals(106, testUser.getElo());
        assertEquals(2, testUser.getWins());
        assertEquals(0, testUser.getLosses());

        assertEquals("_xxXTesterXxx_", testUser.toString());

    }

    @Test
    void removeTagsWhenLoosingEloPoints()
    {
        User testUser = new User();
        testUser.setUsername("Tester");
        testUser.setElo(115);
        testUser.setWins(0);
        testUser.setLosses(0);

        assertEquals("-=##@Tester@##=-", testUser.toString());

        testUser.setStatsLoss();

        assertEquals(110, testUser.getElo());
        assertEquals(0, testUser.getWins());
        assertEquals(1, testUser.getLosses());

        assertEquals("_xxXTesterXxx_", testUser.toString());

        testUser.setStatsLoss();
        testUser.setStatsLoss();

        assertEquals(100, testUser.getElo());
        assertEquals(0, testUser.getWins());
        assertEquals(3, testUser.getLosses());

        assertEquals("Tester", testUser.toString());
    }

}
