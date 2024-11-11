package dev.gest.invest.services;

import dev.gest.invest.dto.StockPriceResponseDto;
import dev.gest.invest.repository.AssetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockPriceService {

    @Value("${yh.api.key}")
    private String apiKey;

    @Value("${yh.api.host}")
    private String apiHost;

    private final GroupSymbolsService groupSymbolsService;
    private final AssetRepository assetRepository;
    private final WebClient webClient;

    public StockPriceService(WebClient.Builder webClientBuilder, GroupSymbolsService groupSymbolsService, AssetRepository assetRepository) {
        this.webClient = webClientBuilder.baseUrl("https://yh-finance.p.rapidapi.com")
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(1024 * 1024))
                .build();
        this.groupSymbolsService = groupSymbolsService;
        this.assetRepository = assetRepository;
    }

    public Mono<Map<String, Double>> fetchPricesForGroupAsync(List<String> group) {
        String symbols = String.join(",", group.getFirst());

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/market/v2/get-quotes")
                        .queryParam("region", "us")
                        .queryParam("symbols", symbols)
                        .build())
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", apiHost)
                .retrieve()
                .bodyToMono(StockPriceResponseDto.class)
                .map(response -> {
                    // Transform data in Map<String, Double> with symbol -> regularMarketPrice
                    return response.getQuoteResponse().getResult().stream()
                            .collect(Collectors.toMap(
                                    StockPriceResponseDto.Result::getSymbol,
                                    StockPriceResponseDto.Result::getRegularMarketPrice
                            ));
                })
                .onErrorResume(e -> {
                    log.error("Error fetching prices for group {}", group, e);
                    return Mono.empty();
                });
    }

}