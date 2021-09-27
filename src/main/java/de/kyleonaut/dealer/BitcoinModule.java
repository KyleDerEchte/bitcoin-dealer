package de.kyleonaut.dealer;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.kyleonaut.dealer.repository.BitcoinRepository;
import de.kyleonaut.dealer.service.BitcoinService;
import de.kyleonaut.dealer.task.BitcoinPriceTask;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 26.09.2021
 */
public class BitcoinModule extends AbstractModule {
    private final BitcoinRepository bitcoinRepository;
    private final BitcoinDealerPlugin plugin;
    private final BitcoinService bitcoinService;
    private final BitcoinPriceTask priceTask;

    public BitcoinModule(BitcoinDealerPlugin plugin) {
        this.plugin = plugin;
        this.bitcoinRepository = new BitcoinRepository();
        this.bitcoinService = new BitcoinService();
        this.priceTask = new BitcoinPriceTask(bitcoinRepository, bitcoinService);
    }

    public Injector getInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        bind(BitcoinPriceTask.class).toInstance(priceTask);
        bind(BitcoinService.class).toInstance(bitcoinService);
        bind(BitcoinDealerPlugin.class).toInstance(this.plugin);
        bind(BitcoinRepository.class).toInstance(this.bitcoinRepository);
    }
}
