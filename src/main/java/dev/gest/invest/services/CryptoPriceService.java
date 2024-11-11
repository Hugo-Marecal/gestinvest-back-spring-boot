package dev.gest.invest.services;

import dev.gest.invest.dto.CryptoPriceResponseDto;
import dev.gest.invest.repository.AssetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CryptoPriceService {

    @Value("${coinmarketcap.api.key}")
    private String apiKey;

    private final GroupSymbolsService groupSymbolsService;
    private final AssetRepository assetRepository;
    private final WebClient webClient;

    public CryptoPriceService(WebClient.Builder webClientBuilder, GroupSymbolsService groupSymbolsService, AssetRepository assetRepository) {
        this.webClient = webClientBuilder.baseUrl("https://pro-api.coinmarketcap.com/v1").build();
        this.groupSymbolsService = groupSymbolsService;
        this.assetRepository = assetRepository;
    }

    public Mono<Map<String, Double>> fetchCryptoPricesForGroupAsync(List<String> group) {
        String symbols = String.join(",", group);

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/cryptocurrency/quotes/latest")
                        .queryParam("symbol", symbols)
                        .build())
                .header("X-CMC_PRO_API_KEY", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CryptoPriceResponseDto>() {
                })
                .map(response -> response.getData().entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().getQuote().get("USD").getPrice()
                        )))
                .onErrorResume(e -> {
                    log.error("Error fetching prices for group {}", group, e);
                    return Mono.empty();
                });
    }

    public Mono<Void> updateCryptoPrices(int categoryId, int groupSize) {
        List<String> groups = groupSymbolsService.getSymbolsInGroups(categoryId, groupSize);

        return Flux.fromIterable(groups)
                .flatMap(group -> {
                    return fetchCryptoPricesForGroupAsync(groups);
                })
                .flatMap(prices -> Flux.fromIterable(prices.entrySet()))
                .flatMap(entry -> {
                    String symbol = entry.getKey();
                    Double price = entry.getValue();

                    return Mono.fromCallable(() -> assetRepository.updatePriceBySymbol(price, symbol))
                            .subscribeOn(Schedulers.boundedElastic())
                            .doOnSuccess(count -> log.info("Price update for {}: {}", symbol, price))
                            .doOnError(e -> log.error("Error updating {}: {}", symbol, e));
                })
                .then();
    }
}
