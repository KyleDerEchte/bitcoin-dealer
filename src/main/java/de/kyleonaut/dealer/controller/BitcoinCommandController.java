package de.kyleonaut.dealer.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.dealer.controller.command.BitcoinCommand;
import de.kyleonaut.dealer.controller.command.BuyCommandController;
import de.kyleonaut.dealer.controller.command.InfoCommandController;
import de.kyleonaut.dealer.controller.command.SellCommandController;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 28.09.2021
 */
@Getter
@Singleton
public class BitcoinCommandController {
    private final Map<String, BitcoinCommand> commands;

    @Inject
    public BitcoinCommandController(BuyCommandController buyCommandController, SellCommandController sellCommandController, InfoCommandController infoCommandController) {
        this.commands = new HashMap<>();
        this.commands.put("buy", buyCommandController);
        this.commands.put("sell", sellCommandController);
        this.commands.put("info", infoCommandController);
    }

    public void execute(String command, Player player, String[] args) {
        commands.forEach((commandName, bitcoinCommand) -> {
            if (command.equals(commandName)) {
                bitcoinCommand.execute(player, args);
            }
        });
    }
}
