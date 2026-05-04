package org.payments.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record Payee(UUID userId, UUID accountId) {
}
