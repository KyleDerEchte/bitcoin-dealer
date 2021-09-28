package de.kyleonaut.dealer.controller.command;

import com.google.inject.Singleton;
import de.kyleonaut.dealer.util.Messages;
import org.bukkit.entity.Player;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 28.09.2021
 */
@Singleton
public class BuyCommandController implements BitcoinCommand {

    @Override
    public void execute(Player player, String[] args) {
        if (!player.hasPermission("bitcoin.dealer.buy")){
            player.sendMessage(Messages.NO_PERMISSION);
            return;
        }
    }
}
