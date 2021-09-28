package de.kyleonaut.dealer.controller;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.dealer.service.BitcoinPriceService;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 27.09.2021
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BitcoinCommandResolver implements CommandExecutor {
    private final BitcoinPriceService service;
    private final BitcoinCommandController commandController;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return false;
        }
        if (strings.length >= 1) {
            commandController.execute(strings[0], player, Arrays.copyOfRange(strings, 1, strings.length));
        }
        return false;
    }

    public ItemStack createBitcoin() {
        final ItemStack itemStack = new ItemStack(Material.SUNFLOWER);
        final ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return itemStack;
        final String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        meta.setLore(Arrays.asList("", "§7Dieser Bitcoin wurde am §e" + date + "§7 gekauft."));
        meta.setDisplayName("§6Bitcoin");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        itemStack.setItemMeta(meta);
        final net.minecraft.server.v1_16_R3.ItemStack nmsCopy = CraftItemStack.asNMSCopy(itemStack);
        final NBTTagCompound nbtTag = nmsCopy.getOrCreateTag();
        nbtTag.setDouble("price", service.getPrice());
        nmsCopy.setTag(nbtTag);
        return CraftItemStack.asBukkitCopy(nmsCopy);
    }
}
