package com.thoughtworks.service;

import com.thoughtworks.domain.Player;
import com.thoughtworks.domain.PlayerGrouping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.thoughtworks.domain.AdministratorTask.coordinatePlayers;
import static com.thoughtworks.domain.PlayerRole.HEALER;
import static com.thoughtworks.domain.PlayerRole.INNOCENT;
import static com.thoughtworks.domain.PlayerRole.KILLER;
import static com.thoughtworks.domain.PlayerRole.availablePlayerRolesWithoutKiller;
import static com.thoughtworks.utils.AdministratorLogFactory.generateLog;
import static com.thoughtworks.utils.LoggerUtil.log;
import static com.thoughtworks.utils.PlayerUtil.getPlayersForRoles;
import static com.thoughtworks.utils.PlayerUtil.getPlayersPerRole;
import static com.thoughtworks.utils.RandomUtil.generatePlayerIds;
import static com.thoughtworks.utils.RandomUtil.generateRandom;
import static com.thoughtworks.utils.RandomUtil.pickPlayers;
import static com.thoughtworks.utils.RandomUtil.randomPlayers;

/**
 * @author Vinodh Kumar Thimmisetty
 */
public class PlayerServiceImpl implements PlayerService {

    @Override
    public List<Player> assignRoles(final int totalPlayers,
                                    final int totalKillers,
                                    final int totalHealers) {
        List<Integer> playerIds = generatePlayerIds(totalPlayers);
        final int noOfInnocents = (totalPlayers - totalKillers - totalHealers);
        return Stream
                .of(pickPlayers(KILLER, totalKillers, playerIds),
                        pickPlayers(HEALER, totalHealers, playerIds),
                        pickPlayers(INNOCENT, noOfInnocents, playerIds))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Player::getPlayerId))
                .collect(Collectors.toList());
    }

    /**
     * Only KILLER is allowed to kill other players.[cannot KILL themself]
     *
     * @param players List of players
     */
    @Override
    public void randomlyKillPlayers(final List<Player> players) {
        final List<Player> killers = getPlayersPerRole(KILLER, players);
        final List<Player> others = getPlayersForRoles(availablePlayerRolesWithoutKiller(), players);

        // Randomly pick any Killer, and randomly kill other players.
        int rounds = 1;
        while (!others.isEmpty()) {
            final Player killer = randomPlayers(killers);
            final Player otherPlayer = randomPlayers(others);
            log("Round - " + rounds++);
            log(String.join(" ", "P" + killer.getPlayerId(), "KILLED", "P" + otherPlayer.getPlayerId()));
            others.remove(otherPlayer);
        }

    }

    @Override
    public void coordinatePlayerKills(List<Player> players) {
        // For each round, certain players will be removed based on administrator constraints.
        final int noOfRoundMax = players.size() - 1;
        int eachRound = 1;
        while (!isKillerNotAvailable(players) && !isOtherPlayersNotAvailable(players)) {
            log("Round - " + eachRound++);
            // Randomly pick any Killer, and randomly kill other players.
            Map<Player, Player> playerActionMap = new HashMap<>();
            for (final Player player : players) {
                final Player randomPlayer = randomPlayersExcludingOwn(player, new ArrayList<>(players));
                playerActionMap.put(player, randomPlayer);
                generateLog(player.getPlayerRole(), player, randomPlayer);
            }
            groupPlayersByRole(playerActionMap);
            players.removeAll(coordinatePlayers(players, groupPlayersByRole(playerActionMap)));
            log("\n");
        }

    }

    private PlayerGrouping groupPlayersByRole(final Map<Player, Player> playerActionMap) {
        Map<Player, Player> killedMap = new HashMap<>();
        Map<Player, Player> healerMap = new HashMap<>();
        Map<Player, Player> suspectMap = new HashMap<>();

        for (final Map.Entry<Player, Player> eachPlayer : playerActionMap.entrySet()) {
            final Player playerHashKey = eachPlayer.getKey();
            if (playerHashKey.getPlayerRole().equals(KILLER)) {
                killedMap.put(playerHashKey, eachPlayer.getValue());
            } else if (playerHashKey.getPlayerRole().equals(HEALER)) {
                healerMap.put(playerHashKey, eachPlayer.getValue());
            } else {
                suspectMap.put(playerHashKey, eachPlayer.getValue());
            }

        }
        return new PlayerGrouping(killedMap, healerMap, suspectMap);
    }

    private boolean isOtherPlayersNotAvailable(final List<Player> players) {
        return getPlayersForRoles(availablePlayerRolesWithoutKiller(), players).isEmpty();
    }

    private boolean isKillerNotAvailable(final List<Player> players) {
        return getPlayersPerRole(KILLER, players).isEmpty();
    }

    private Player randomPlayersExcludingOwn(final Player player, final List<Player> players) {
        // Exclude Same ROLE or SAME Player ID
        players.removeIf(c -> c.getPlayerId() == player.getPlayerId());
//        if (KILLER.equals(player.getPlayerRole())) {
//            players.removeIf(c -> c.getPlayerRole().equals(player.getPlayerRole()));
//        }
        return players.get(generateRandom(players.size()));
    }
}
