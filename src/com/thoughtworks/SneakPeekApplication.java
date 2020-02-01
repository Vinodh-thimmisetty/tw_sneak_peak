package com.thoughtworks;


import com.thoughtworks.domain.Player;
import com.thoughtworks.service.PlayerService;
import com.thoughtworks.service.PlayerServiceImpl;

import java.util.List;
import java.util.Scanner;

import static com.thoughtworks.utils.LoggerUtil.log;
import static java.lang.String.join;

/**
 * @author Vinodh Kumar Thimmisetty
 */
public class SneakPeekApplication {

    public static void main(String[] args) {

        PlayerService ps = new PlayerServiceImpl();
        Scanner sc = new Scanner(System.in);

        log("HOW MANY PLAYERS ARE PARTICIPATING IN THE GAME ?");
        final int noOfPlayers = sc.nextInt();
        log("HOW MANY KILLERS ?");
        final int noOfKillers = sc.nextInt();
        log("HOW MANY HEALERS ?");
        final int noOfHealers = sc.nextInt();

        final List<Player> players = ps.assignRoles(noOfPlayers, noOfKillers, noOfHealers);
        players.forEach(x ->
                log(join(" ",
                        "P" + x.getPlayerId(),
                        x.getPlayerRole().toString())));

//        System.out.println("--- Killers starts killing players ---- ");
//        ps.randomlyKillPlayers(players);

        System.out.println("--- Administration started the coordination of players ---- ");
        ps.coordinatePlayerKills(players);


    }

}
