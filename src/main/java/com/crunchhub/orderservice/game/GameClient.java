package com.crunchhub.orderservice.game;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class GameClient {
    private final static String GAMES_ROOT_API = "/games/name/";
    private final WebClient webClient;

    public GameClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Game> getGameByName(String name) {
        return webClient.get()
                .uri(GAMES_ROOT_API + name)
                .retrieve()
                .bodyToMono(Game.class)
                .timeout(Duration.ofSeconds(3), Mono.empty())
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> Mono.empty())
                .retryWhen(
                        Retry.backoff(3,  Duration.ofMillis(100))
                )
                .onErrorResume(Exception.class, ex -> Mono.empty());
    }
}
