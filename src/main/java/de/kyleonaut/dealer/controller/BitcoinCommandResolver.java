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
        } else {
            commandController.execute("info", player, strings);
        }
        return false;
    }
}
