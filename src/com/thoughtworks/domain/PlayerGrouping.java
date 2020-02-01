package com.thoughtworks.domain;

import java.util.Map;

/**
 * @author Vinodh Kumar Thimmisetty
 */
public class PlayerGrouping {

    Map<Player, Player> killedMap, healerMap, suspectMap;

    public PlayerGrouping(Map<Player, Player> killedMap,
                          Map<Player, Player> healerMap,
                          Map<Player, Player> suspectMap) {
        this.killedMap = killedMap;
        this.healerMap = healerMap;
        this.suspectMap = suspectMap;
    }

    public Map<Player, Player> getKilledMap() {
        return killedMap;
    }

    public Map<Player, Player> getHealerMap() {
        return healerMap;
    }

    public Map<Player, Player> getSuspectMap() {
        return suspectMap;
    }
}
