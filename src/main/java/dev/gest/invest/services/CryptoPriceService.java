package dev.gest.invest.services;

import dev.gest.invest.dto.CryptoPriceResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CryptoPriceService {

    @Value("${coinmarketcap.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public CryptoPriceService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://pro-api.coinmarketcap.com/v1").build();
    }

    public Mono<Map<String, Double>> fetchPricesForGroupAsync(List<String> group) {
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
}
