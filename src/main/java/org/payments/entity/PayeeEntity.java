package org.payments.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "payee",
        indexes = {
                @Index(name = "idx_account_id", columnList = "account_id"),
        })
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayeeEntity {
    @Id
    private UUID id;

    @Column(name = "account_id")
    private UUID accountId;
}
