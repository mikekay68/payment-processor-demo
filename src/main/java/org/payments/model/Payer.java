package org.payments.model;

import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record Payer(String name, @Nullable String org) {
}
