package com.thoughtworks.utils;

import com.thoughtworks.domain.Player;
import com.thoughtworks.domain.PlayerRole;

import static com.thoughtworks.utils.LoggerUtil.log;

/**
 * @author Vinodh Kumar Thimmisetty
 */
public class AdministratorLogFactory {

    public static void generateLog(final PlayerRole playerRole,
                                   final Player actionFrom,
                                   final Player actionOn) {

        switch (playerRole) {

            case KILLER:
                log(String.join(" ", "P" + actionFrom.getPlayerId(),
                        "Killed",
                        "P" + actionOn.getPlayerId()));
                break;
            case INNOCENT:
                log(String.join(" ", "P" + actionFrom.getPlayerId(),
                        "SUSPECTED",
                        "P" + actionOn.getPlayerId()));
                break;
            default:
                break;
        }

    }
}
