package com.thoughtworks.utils;

import com.thoughtworks.domain.Player;
import com.thoughtworks.domain.PlayerRole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Vinodh Kumar Thimmisetty
 */
public class RandomUtil {

    public static List<Integer> generatePlayerIds(final int noOfPlayers) {
        return IntStream.range(1, noOfPlayers + 1).boxed().collect(Collectors.toList());
    }

    public static List<Player> pickPlayers(final PlayerRole playerRole,
                                           final int noOfPlayers,
                                           List<Integer> availablePlayerIds) {
        if (!(noOfPlayers > 0)) return Collections.emptyList();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < noOfPlayers; i++) {
            final Integer randomPlayerId = randomPlayerId(availablePlayerIds);
            players.add(new Player(randomPlayerId, playerRole));
            availablePlayerIds.remove(randomPlayerId);
        }
        return players;
    }

    public static Integer randomPlayerId(List<Integer> availablePlayerIds) {
        return availablePlayerIds.get(generateRandom(availablePlayerIds.size()));
    }

    public static int generateRandom(int upperLimit) {
        return new Random().nextInt(upperLimit);
    }

    public static Player randomPlayers(List<Player> availablePlayers) {
        return availablePlayers.get(generateRandom(availablePlayers.size()));
    }

}
