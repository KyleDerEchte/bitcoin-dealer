package de.kyleonaut.dealer.controller.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.dealer.service.BitcoinPriceService;
import de.kyleonaut.dealer.util.Messages;
import lombok.RequiredArgsConstructor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 28.09.2021
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SellCommandController implements BitcoinCommand {
    private final Economy economy;
    private final BitcoinPriceService priceService;

    @Override
    public void execute(Player player, String[] args) {
        if (!player.hasPermission("bitcoin.dealer.sell")) {
            player.sendMessage(Messages.NO_PERMISSION);
            return;
        }
        final ItemStack itemStack = player.getInventory().getItemInMainHand();
        final net.minecraft.server.v1_16_R3.ItemStack nmsCopy = CraftItemStack.asNMSCopy(itemStack);
        if (nmsCopy.getTag() != null && nmsCopy.getTag().hasKey("price")) {
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());
            final double toDeposit = priceService.getPrice() * 0.95;
            final long longValue = Math.round(toDeposit);
            player.sendMessage(String.format(Messages.PAYED_N_MONEY, longValue));
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            final double price = nmsCopy.getTag().getDouble("price");
            player.getInventory().addItem(getBill(price, player.getName()));
            economy.depositPlayer(offlinePlayer, toDeposit);
        } else {
            player.sendMessage(Messages.CANT_SELL_THIS_ITEM);
        }
    }


    public ItemStack getBill(double price, String playerName) {
        final ItemStack itemStack = new ItemStack(Material.PAPER);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return itemStack;
        itemMeta.setDisplayName("§6Rechnung");
        double res = Math.round(priceService.getPrice() * 0.95 - price);
        if (res == 0) {
            itemMeta.setLore(Arrays.asList("", "§e" + playerName + " §7hat durch den Verkauf eines Bitcoins §ekeinen Gewinn§7 erzielt."));
        } else if (res > 0) {
            itemMeta.setLore(Arrays.asList("", "§e" + playerName + " §7hat durch den Verkauf eines Bitcoins §e" + res + "$ Gewinn§7 erzielt."));
        } else {
            itemMeta.setLore(Arrays.asList("", "§e" + playerName + " §7hat durch den Verkauf eines Bitcoins §e" + res + "$ Verlust§7 gemacht."));
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
