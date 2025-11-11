package com.crunchhub.orderservice.order.web;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(
        @NotBlank(message = "The name of the game must be defined.")
        String name,

        @NotNull(message = "The tip amount must be defined (no tip should be set to 0)")
        @Min(value = 0, message = "Your tip amount cannot be negative.")
        @Max(value = 1000, message = "Your tip amount cannot be more than $1000.00")
        Double tip
) {
}
