package de.kyleonaut.dealer.service;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 27.09.2021
 */
public class BitcoinService {
    private double price;

    public synchronized double getPrice() {
        return price;
    }

    public synchronized void setPrice(double price) {
        this.price = price;
    }
}
