package com.crunchhub.orderservice.order.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("orders")
public record Order(
    @Id
    Long id,

    String gameName,
    String gameCreator,
    Double gamePrice,
    Double tip,
    OrderStatus status,

    @CreatedDate
    Instant createdDate,

    @LastModifiedDate
    Instant lastModifiedDate,

    @Version
    int version
) {
    public static Order of(
            String gameName, String gameCreator, Double gamePrice, Double tip, OrderStatus status
    ) {
        return new Order(null, gameName, gameCreator, gamePrice, tip, status, null, null, 0);
    }
}
