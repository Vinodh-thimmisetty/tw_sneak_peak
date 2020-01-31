package com.thoughtworks.service;


import com.thoughtworks.domain.Player;

import java.util.List;

/**
 * @author Vinodh Kumar Thimmisetty
 */
public interface PlayerService {

    List<Player> assignRoles(int totalPlayers, int totalKillers, int totalHealers);

    void randomlyKillPlayers(List<Player> players);

    void coordinatePlayerKills(List<Player> players);
}
