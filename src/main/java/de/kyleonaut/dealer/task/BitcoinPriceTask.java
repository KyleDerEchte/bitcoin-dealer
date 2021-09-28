package de.kyleonaut.dealer.task;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.dealer.repository.BitcoinRepository;
import de.kyleonaut.dealer.service.BitcoinPriceService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 27.09.2021
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BitcoinPriceTask implements Runnable {
    private final BitcoinRepository repository;
    private final BitcoinPriceService service;

    @Setter
    private boolean isRunning = true;

    @Override
    public void run() {
        while (isRunning) {
            try {
                final Double price = repository.getPrice();
                service.setPrice(price);
                Bukkit.getLogger().log(Level.INFO, "[BitcoinDealer] Update price to: " + price + " USD.");
                TimeUnit.MINUTES.sleep(20);
            } catch (IOException | ParseException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
