package de.kyleonaut.dealer;

import com.google.inject.Inject;
import com.google.inject.Injector;
import de.kyleonaut.dealer.task.BitcoinPriceTask;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 26.09.2021
 */
public class BitcoinDealerPlugin extends JavaPlugin {
    @Inject
    private BitcoinPriceTask priceTask;
    private Thread thread;

    @SneakyThrows
    @Override
    public void onEnable() {
        final BitcoinModule module = new BitcoinModule(this);
        Injector injector = module.getInjector();
        injector.injectMembers(this);
        this.thread = new Thread(priceTask);
        this.thread.start();
        Bukkit.getLogger().log(Level.INFO, "[BitcoinDealer] Start PriceTask.");
    }

    @Override
    public void onDisable() {
        priceTask.setRunning(false);
        this.thread.stop();
    }
}
