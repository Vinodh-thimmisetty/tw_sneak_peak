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
                                                 final PlayerGrouping playerGrouping) {

        if (playerGrouping.getKilledMap().size() == 1 &&
                playerGrouping.getSuspectMap().size() == 1) {
            for (final Map.Entry<Player, Player> eachKiller : playerGrouping.getKilledMap().entrySet()) {
                final Player currentKiller = eachKiller.getKey();
                final Player currentToBeKilled = eachKiller.getValue();

                validateAndRemovePlayers(players,
                        "Killer P" + currentKiller.getPlayerId() + " suspected P" + currentToBeKilled.getPlayerId() + " and  Killed",
                        playerGrouping.getHealerMap(), currentKiller, currentToBeKilled);
            }
//            return Collections.emptyList();
        }
        List<Player> playersToRemove = new ArrayList<>();
        Map<Player, Integer> suspectCountPerPlayer = new HashMap<>();
        for (final Map.Entry<Player, Player> eachSuspectedPlayer : playerGrouping.getSuspectMap().entrySet()) {
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
        for (final Map.Entry<Player, Player> eachKiller : playerGrouping.getKilledMap().entrySet()) {
            final Player killer = eachKiller.getKey();
            final Player to_be_killed = eachKiller.getValue();
            validateAndRemovePlayers(players,
                    "Killer P" + killer.getPlayerId() + " suspected P" + to_be_killed.getPlayerId() + " and  Killed",
                    playerGrouping.getHealerMap(), to_be_killed);
            if (playersToRemove.contains(killer)) {
                playersToRemove.remove(killer);
                validateAndRemovePlayers(players,
                        "Killer P" + killer.getPlayerId() + " is Killed",
                        playerGrouping.getHealerMap(), killer);
            }
        }

        for (final Player player : playersToRemove) {
            validateAndRemovePlayers(players,
                    "More than one Innocents identified wrong killer ---> So, co-ordinator killed P" + player.getPlayerId(),
                    playerGrouping.getHealerMap(), player);
        }

        return players;

    }

    private static void validateAndRemovePlayers(final List<Player> players,
                                                 final String logMessage,
                                                 final Map<Player, Player> healerMap,
                                                 final Player... playersToKill) {


        if (healerMap.isEmpty()) {
            log(logMessage);
            players.remove(playersToKill);
        } else {
            // Remove the player only is player is not healed.
            for (final Player player_to_be_killed : playersToKill) {
                for (final Map.Entry<Player, Player> healer : healerMap.entrySet()) {
                    if (healer.getValue().equals(player_to_be_killed)) {
                        log("P" + player_to_be_killed.getPlayerId() + " is healed by healer P" + healer.getKey().getPlayerId());
                    } else {
                        log(logMessage);
                        players.remove(player_to_be_killed);
                    }
                }
            }
        }


    }

}
