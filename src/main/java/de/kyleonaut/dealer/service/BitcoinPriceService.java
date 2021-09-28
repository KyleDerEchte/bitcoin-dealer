package de.kyleonaut.dealer.service;

import com.google.inject.Singleton;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 27.09.2021
 */
@Singleton
public class BitcoinPriceService {
    private double price;

    public synchronized double getPrice() {
        return price;
    }

    public synchronized void setPrice(double price) {
        this.price = price;
    }
}
