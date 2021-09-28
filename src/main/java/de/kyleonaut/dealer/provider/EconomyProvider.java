package de.kyleonaut.dealer.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.kyleonaut.dealer.BitcoinDealerPlugin;
import lombok.RequiredArgsConstructor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 28.09.2021
 */
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class EconomyProvider implements Provider<Economy> {
    private final BitcoinDealerPlugin plugin;

    @Override
    public Economy get() {
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        return rsp.getProvider();
    }
}
