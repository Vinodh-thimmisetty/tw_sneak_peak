package com.thoughtworks.utils;

import com.thoughtworks.domain.Player;
import com.thoughtworks.domain.PlayerRole;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vinodh Kumar Thimmisetty
 */
public class PlayerUtil {

    public static List<Player> getPlayersPerRole(final PlayerRole playerRole,
                                                 final List<Player> players) {
        if (playerRole == null) return Collections.EMPTY_LIST;
        return players.stream()
                .filter(x -> playerRole.equals(x.getPlayerRole()))
                .collect(Collectors.toList());
    }

    public static List<Player> getPlayersForRoles(final List<PlayerRole> playerRoles,
                                                  final List<Player> players) {
        if (playerRoles.isEmpty()) return Collections.EMPTY_LIST;
        return players.stream()
                .filter(x -> playerRoles.contains(x.getPlayerRole()))
                .collect(Collectors.toList());
    }
}
