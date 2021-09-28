package de.kyleonaut.dealer.controller.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.dealer.service.BitcoinPriceService;
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
        player.sendMessage("§7Aktueller Bitcoin Preis: " + priceService.getPrice());
    }
}
