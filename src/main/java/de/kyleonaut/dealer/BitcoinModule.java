package de.kyleonaut.dealer;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.kyleonaut.dealer.provider.EconomyProvider;
import net.milkbowl.vault.economy.Economy;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 26.09.2021
 */
public class BitcoinModule extends AbstractModule {
    private final BitcoinDealerPlugin plugin;

    public BitcoinModule(BitcoinDealerPlugin plugin) {
        this.plugin = plugin;
    }

    public Injector getInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        bind(BitcoinDealerPlugin.class).toInstance(this.plugin);
        bind(Economy.class).toProvider(EconomyProvider.class);
    }
}
