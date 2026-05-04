package org.payments.service;

import lombok.extern.slf4j.Slf4j;
import org.payments.model.Payment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentProducer {

    private final KafkaTemplate<String, Payment> kafkaTemplate;

    public PaymentProducer(KafkaTemplate<String, Payment> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPayment(Payment payment) {
        kafkaTemplate.send("payments", payment).whenComplete((result, e) -> {
            if (e != null) {
                log.error("Failed to send payment with id={}. Error={}", payment.id(), e.getMessage());
            }
        });
    }
}
