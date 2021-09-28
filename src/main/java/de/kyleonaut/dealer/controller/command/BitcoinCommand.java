package de.kyleonaut.dealer.controller.command;

import org.bukkit.entity.Player;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 28.09.2021
 */
public interface BitcoinCommand {
    void execute(Player player, String[] args);
}
