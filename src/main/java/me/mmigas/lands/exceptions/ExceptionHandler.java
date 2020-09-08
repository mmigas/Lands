package me.mmigas.lands.exceptions;

import org.bukkit.entity.Player;

public class ExceptionHandler {

    private ExceptionHandler() {

    }

    public static boolean handle(Runnable method, Player player) {
        try {
            method.run();
        } catch (InvalidBuyLandException e) {
            player.sendMessage("Not a valid location to buy");
            return false;
        }
        return true;
    }
}
