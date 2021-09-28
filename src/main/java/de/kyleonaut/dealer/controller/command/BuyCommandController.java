package de.kyleonaut.dealer.controller.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.dealer.service.BitcoinPriceService;
import de.kyleonaut.dealer.util.Messages;
import lombok.RequiredArgsConstructor;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 28.09.2021
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BuyCommandController implements BitcoinCommand {
    private final Economy economy;
    private final BitcoinPriceService priceService;

    @Override
    public void execute(Player player, String[] args) {
        if (!player.hasPermission("bitcoin.dealer.buy")) {
            player.sendMessage(Messages.NO_PERMISSION);
            return;
        }
        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());
        if (economy.getBalance(offlinePlayer) < priceService.getPrice()) {
            player.sendMessage(Messages.NOT_ENOUGH_MONEY);
            return;
        } else {
            economy.withdrawPlayer(offlinePlayer, priceService.getPrice());
            player.getInventory().addItem(createBitcoin());
        }
    }

    public ItemStack createBitcoin() {
        final ItemStack itemStack = new ItemStack(Material.SUNFLOWER);
        final ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        final String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        final long price = Math.round(priceService.getPrice());
        meta.setLore(Arrays.asList("", "§7Dieser Bitcoin wurde am §e" + date + "§7 gekauft.", "§7Gerundeter Kaufpreis: §e" + price + "$"));
        meta.setDisplayName("§6Bitcoin");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemStack.setItemMeta(meta);
        final net.minecraft.server.v1_16_R3.ItemStack nmsCopy = CraftItemStack.asNMSCopy(itemStack);
        final NBTTagCompound nbtTag = nmsCopy.getOrCreateTag();
        nbtTag.setDouble("price", priceService.getPrice());
        nbtTag.setString("uuid", UUID.randomUUID().toString());
        nmsCopy.setTag(nbtTag);
        return CraftItemStack.asBukkitCopy(nmsCopy);
    }
}
