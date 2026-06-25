package com.currency.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")   // allows frontend to call this API
public class CurrencyController {

    // Your API key from the original project
    private static final String API_KEY = "f43a4db34d937078f6330193";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest";

    private final WebClient webClient;

    @Autowired
    public CurrencyController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    /**
     * GET /api/convert?from=USD&to=INR&amount=100
     * Calls the exchange rate API and returns the converted amount.
     */
    @GetMapping("/convert")
    public ResponseEntity<ConversionResponse> convert(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount) {

        // Fetch live rates from exchangerate-api.com
        Map<?, ?> apiResponse = webClient
                .get()
                .uri("/" + from.toUpperCase())
                .retrieve()
                .bodyToMono(Map.class)
                .block();  // block() makes it synchronous (simple for learning)

        if (apiResponse == null || !apiResponse.containsKey("conversion_rates")) {
            return ResponseEntity.badRequest().build();
        }

        // Get all rates from the response
        @SuppressWarnings("unchecked")
        Map<String, Double> rates = (Map<String, Double>) apiResponse.get("conversion_rates");

        // Look up the target currency rate
        Double rate = rates.get(to.toUpperCase());
        if (rate == null) {
            return ResponseEntity.badRequest().build();
        }

        double convertedAmount = amount * rate;

        return ResponseEntity.ok(new ConversionResponse(from, to, amount, rate, convertedAmount));
    }
}
