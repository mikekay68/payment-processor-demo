package org.payments.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record Payment(
        UUID id,
        Payee payee,
        Payer payer,
        BigDecimal amount

) {
}
