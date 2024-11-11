package dev.gest.invest.scheduler;

import dev.gest.invest.services.CryptoPriceService;
import dev.gest.invest.services.StockPriceService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceUpdateScheduler {

    private final CryptoPriceService cryptoPriceService;
    private final StockPriceService stockPriceService;


    public PriceUpdateScheduler(CryptoPriceService cryptoPriceService, StockPriceService stockPriceService) {
        this.cryptoPriceService = cryptoPriceService;
        this.stockPriceService = stockPriceService;
    }

    @Scheduled(cron = "0 6 17 * * *")
    public void updateCryptoPricesJob() {
        cryptoPriceService.updateCryptoPrices(1, 60)
                .subscribe(
                        unused -> System.out.println("Successful price update"),
                        error -> System.err.println("Error when updating prices: " + error)
                );
    }

    @Scheduled(cron = "0 6 17 * * *")
    public void updateStockPricesJob() {
        stockPriceService.updateStockPrices(2, 40)
                .subscribe(
                        unused -> System.out.println("Successful price update"),
                        error -> System.err.println("Error when updating prices: " + error)
                );
    }
}

