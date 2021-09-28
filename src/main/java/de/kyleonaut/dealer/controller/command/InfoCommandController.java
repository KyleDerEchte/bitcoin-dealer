package de.kyleonaut.dealer.controller.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.dealer.service.BitcoinPriceService;
import de.kyleonaut.dealer.util.Messages;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 28.09.2021
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class InfoCommandController implements BitcoinCommand {
    private final BitcoinPriceService priceService;

    @Override
    public void execute(Player player, String[] args) {
        if (!player.hasPermission("bitcoin.dealer.info")) {
            player.sendMessage(Messages.NO_PERMISSION);
            return;
        }
        player.sendMessage("§7=== §8[§eBitcoin§8] §7===");
        player.sendMessage("§e/btc buy <Anzahl> §7- Kaufe Bitcoins");
        player.sendMessage("§e/btc sell §7- Verkaufe Bitcoins");
        player.sendMessage("§e/btc info §7- Zeigt die Info an");
        player.sendMessage("§7Aktueller Bitcoin Kurs: §e" + priceService.getPrice() + "$");
    }
}
