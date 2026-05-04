package org.payments.model;

import java.math.BigDecimal;

public record Payment(
        Payee payee,
        Payer payer,
        BigDecimal amount

) {
}
