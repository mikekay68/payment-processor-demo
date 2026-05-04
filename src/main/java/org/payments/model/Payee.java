package org.payments.model;

import java.util.UUID;

public record Payee(UUID userId, UUID accountId) {
}
