package com.thoughtworks.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Vinodh Kumar Thimmisetty
 */
public enum PlayerRole {

    KILLER, HEALER, INNOCENT;

    public static List<PlayerRole> availablePlayerRolesWithoutKiller() {
        return Stream.of(PlayerRole.values()).filter(r -> !r.equals(PlayerRole.KILLER)).collect(Collectors.toList());
    }

}
