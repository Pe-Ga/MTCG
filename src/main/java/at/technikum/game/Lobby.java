package at.technikum.game;

import at.technikum.application.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Lobby implements Runnable {

    private List<User> queue = new ArrayList<User>();

    private Map<User, String> battleLog = new HashMap<User, String>();

    public Lobby() {}

    public Map<User, String> getBattleLog() {
        return battleLog;
    }

    public void setBattleLog(Map<User, String> battleLog) {
        this.battleLog = battleLog;
    }

    public synchronized boolean addParticipant(User participant)
    {
        if (queue.contains(participant))
        {
            System.out.println("User: " + participant + " already in queue");
            return false;
        }

        if(participant.getDeck() != null && participant.getDeck().size() != 4)
        {
            System.out.println("User: " + participant + " does not have EXACTLY 4 cards in their deck.");
            return false;
        }

        System.out.println("Lobby will now add " + participant + " to queue in Lobby.");
        this.queue.add(participant);
        return true;
    }

    public void run() {
        System.out.println("Lobby started observing its queue.");
        while (true)
        {
            String battleReport;

            // wait for at least 2 participants in queue
            while (this.queue.size() < 2)
            {
                System.out.println("Lobby still waiting for 2 participants... (currently has " + this.queue.size() + ")");
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Lobby recognized 2 or more players are waiting in the queue.");

            User combatantA = this.queue.remove(0);
            User combatantB = this.queue.remove(0);

            System.out.println("Lobby removed two players from queue and sent them to battle.");

            // start a battle with the first two waiting participants (and remove those from queue)
            Battle battle = new Battle(combatantA, combatantB);
            battleReport = battle.fightBattle();
            battleLog.put(combatantA, battleReport);
            battleLog.put(combatantB, battleReport);
        }
    }

    @Override
    public String toString()
    {
        String out = "Waiting users:";
        if(this.queue.isEmpty())
            out += " None, no users waiting in lobby.";
        else
        {
            for (User user : this.queue)
                out += " " + user;
        }
        return out;
    }
}
