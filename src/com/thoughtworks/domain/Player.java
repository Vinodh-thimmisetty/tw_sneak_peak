package com.thoughtworks.domain;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vinodh Kumar Thimmisetty
 */
public class Player {
    private int playerId;
    private PlayerRole playerRole;

    public Player(final int playerId, final PlayerRole playerRole) {
        this.playerId = playerId;
        this.playerRole = playerRole;
    }

    public int getPlayerId() {
        return playerId;
    }

    public PlayerRole getPlayerRole() {
        return playerRole;
    }


}
