package com.thoughtworks.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.thoughtworks.utils.LoggerUtil.log;

/**
 * @author Vinodh Kumar Thimmisetty
 */
public class AdministratorTask {

    // If return TRUE, Game is FINISHED.
    public static List<Player> coordinatePlayers(List<Player> players,
                                                 final Map<Player, Player> killedPlayers,
                                                 final Map<Player, Player> suspectedPlayers) {

        if (killedPlayers.size() == 1 &&
                suspectedPlayers.size() == 1) {
            for (final Map.Entry<Player, Player> eachKiller : killedPlayers.entrySet()) {
                final Player currentKiller = eachKiller.getKey();
                final Player currentToBeKilled = eachKiller.getValue();
                log("Killer P" + currentKiller.getPlayerId() + " suspected P" + currentToBeKilled.getPlayerId() + " and  Killed");
                players.remove(currentKiller);
                players.remove(currentToBeKilled);
            }
            return Collections.emptyList();
        }
        List<Player> playersToRemove = new ArrayList<>();
        Map<Player, Integer> suspectCountPerPlayer = new HashMap<>();
        for (final Map.Entry<Player, Player> eachSuspectedPlayer : suspectedPlayers.entrySet()) {
            final Player suspectedHashKey = eachSuspectedPlayer.getValue();
            if (suspectCountPerPlayer.containsKey(suspectedHashKey)) {
                final Integer suspectCount = suspectCountPerPlayer.get(suspectedHashKey) + 1;
                suspectCountPerPlayer.put(suspectedHashKey, suspectCount);
            } else {
                suspectCountPerPlayer.put(suspectedHashKey, 1);
            }
        }
        int MIN_SUSPECTS = 2;
        for (final Map.Entry<Player, Integer> eachSuspect : suspectCountPerPlayer.entrySet()) {
            final Integer value = eachSuspect.getValue();
            if (value >= MIN_SUSPECTS) {
                MIN_SUSPECTS = value;
                playersToRemove.add(eachSuspect.getKey()); // more than one suspects.
            }
        }


        // Irrespective of Suspects, remove the players killed by KILLER.
        for (final Map.Entry<Player, Player> eachKiller : killedPlayers.entrySet()) {
            final Player killer = eachKiller.getKey();
            final Player to_be_killed = eachKiller.getValue();
            log("Killer P" + killer.getPlayerId() + " suspected P" + to_be_killed.getPlayerId() + " and  Killed");
            players.remove(to_be_killed);
            if (playersToRemove.contains(killer)) {
                log("Killer P" + killer.getPlayerId() + " is Killed");
                playersToRemove.remove(killer);
                players.remove(killer);
            }
        }

        for (final Player player : playersToRemove) {
            log("More than one Innocents identified wrong killer ---> So, co-ordinator killed P" + player.getPlayerId());
            players.remove(player);
        }

        return players;

    }

}
