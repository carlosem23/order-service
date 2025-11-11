package com.crunchhub.orderservice.game;

// DTO for Game
public record Game(
        String name,
        String creator,
        Double price
) { }
