package com.crunchhub.orderservice.order.domain;

import com.crunchhub.orderservice.game.Game;
import com.crunchhub.orderservice.game.GameClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
    private final GameClient gameClient;
    private final  OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository,  GameClient gameClient) {
        this.orderRepository = orderRepository;
        this.gameClient = gameClient;
    }

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public Mono<Order> submitOrder(String name, Double tip) {
        return gameClient.getGameByName(name).map(game -> buildAcceptedOrder(game, tip))
                .defaultIfEmpty(buildRejectedOrder(name, tip))
                .flatMap(orderRepository::save);
    }

    public static Order buildAcceptedOrder(Game game, Double tip) {
        return Order.of(game.name(), game.creator(), game.price(), tip, OrderStatus.ACCEPTED);
    }
    public static Order buildRejectedOrder(String name, Double tip) {
        return Order.of(name, null, null, tip, OrderStatus.REJECTED);
    }
}
