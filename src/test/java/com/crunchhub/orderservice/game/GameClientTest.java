package com.crunchhub.orderservice.game;

import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class GameClientTest {
    private MockWebServer mockWebServer;
    private GameClient gameClient;

    @BeforeEach
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        var webClient = WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build();
        this.gameClient = new GameClient(webClient);
    }
    @AfterEach
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void whenGameExistsReturnGame() {
        var gameName = "Test";

        var mockResponse = new MockResponse().addHeader("Content-Type", "application/json")
                .setBody("""
                        {
                            "name": "%s",
                            "creator": "TestCreator",
                            "price": 19.90,
                            "genre": "MOBA"
                        }
                        """.formatted(gameName));
        mockWebServer.enqueue(mockResponse);
        var game = gameClient.getGameByName(gameName);

        StepVerifier.create(game)
                .expectNextMatches(
                        g -> g.name().equals(gameName)
                ).verifyComplete();
    }
}
