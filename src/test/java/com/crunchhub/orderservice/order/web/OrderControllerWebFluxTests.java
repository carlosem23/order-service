package com.crunchhub.orderservice.order.web;

import com.crunchhub.orderservice.order.domain.Order;
import com.crunchhub.orderservice.order.domain.OrderService;
import com.crunchhub.orderservice.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebFluxTest(OrderController.class)
public class OrderControllerWebFluxTests {
    @Autowired
    private WebTestClient webClient;

    @MockitoBean
    private OrderService orderService;

    @Test
    public void whenGameNotAvailableRejectOrder() {
        var orderRequest = new OrderRequest("Test", 9.00);
        var expectedOrder = OrderService.buildRejectedOrder(
                orderRequest.name(), orderRequest.tip());
        given(orderService.submitOrder(orderRequest.name(), orderRequest.tip()))
                .willReturn(Mono.just(expectedOrder));

        webClient
                .post()
                .uri("/orders/")
                .bodyValue(orderRequest)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Order.class).value(actualOrder -> {
                    assertThat(actualOrder).isNotNull();
                    assertThat(actualOrder.status()).isEqualTo(OrderStatus.REJECTED);
                });
    }

}
