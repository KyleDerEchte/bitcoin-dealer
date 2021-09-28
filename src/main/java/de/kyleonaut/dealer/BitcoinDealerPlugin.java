package de.kyleonaut.dealer;

import com.google.inject.Inject;
import com.google.inject.Injector;
import de.kyleonaut.dealer.controller.BitcoinCommandResolver;
import de.kyleonaut.dealer.task.BitcoinPriceTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 26.09.2021
 */
public class BitcoinDealerPlugin extends JavaPlugin {
    private Thread thread;

    @Inject
    private BitcoinPriceTask priceTask;

    @Inject
    private BitcoinCommandResolver bitcoinCommandResolver;

    @Getter
    private Injector injector;

    @Override
    public void onEnable() {
        final BitcoinModule module = new BitcoinModule(this);
        this.injector = module.getInjector();
        this.injector.injectMembers(this);
        this.thread = new Thread(priceTask);
        this.thread.start();
        Bukkit.getLogger().log(Level.INFO, "[BitcoinDealer] Start PriceTask.");
        Objects.requireNonNull(getCommand("btc")).setExecutor(bitcoinCommandResolver);
        Objects.requireNonNull(getCommand("btc")).setTabCompleter(bitcoinCommandResolver);
    }

    @Override
    public void onDisable() {
        priceTask.setRunning(false);
        this.thread.stop();
    }
}
