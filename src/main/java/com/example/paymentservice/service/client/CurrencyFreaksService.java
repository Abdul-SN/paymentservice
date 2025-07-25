package com.example.paymentservice.service.client;

import com.example.paymentservice.client.CurrencyFreaksClient;
import com.example.paymentservice.controller.dto.rate.LatestRatesResponse;
import com.example.paymentservice.error.exception.CurrencyUnavailableException;
import com.example.paymentservice.model.enums.CurrencyType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyFreaksService {

    private final CurrencyFreaksClient currencyFreaksClient;
    private final CacheManager cacheManager;

    @Value("${currencyfreaks.api.key}")
    private String apiKey;

    /**
     * Возвращает обновленные курсы валют, кэширует результат на 60 секунд
     * в кэше с именем «currencyRates».
     */
    @Cacheable(
            value = "currencyRates",
            key = "#source.name + '_' + #dest.name"
    )
    public LatestRatesResponse getLatest(CurrencyType source, CurrencyType dest) {
        try {
            return currencyFreaksClient.getLatestRates(
                    apiKey, source.getName() + "," + dest.getName()
            );
        } catch (Exception e) {
            log.error("Failed to fetch latest rates {}→{}: {}",
                    source.getName(), dest.getName(), e.getMessage());
            // Фолбек на кеш:
            String cacheKey = source.getName() + ":" + dest.getName();
            Cache cache = cacheManager.getCache("currencyRates");
            if (cache != null) {
                LatestRatesResponse cached = cache.get(cacheKey, LatestRatesResponse.class);
                if (cached != null) {
                    log.warn("Returning stale rate from cache for key {}", cacheKey);
                    return cached;
                }
            }
            throw new CurrencyUnavailableException(
                    "Currency rates unavailable and no cached data present"
            );
        }
    }

}
