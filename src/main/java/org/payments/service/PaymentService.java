package org.payments.service;

import org.payments.entity.PayeeEntity;
import org.payments.entity.PayerEntity;
import org.payments.entity.PaymentEntity;
import org.payments.model.Payment;
import org.payments.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentProducer paymentProducer;


    public PaymentService(PaymentRepository paymentRepository, PaymentProducer paymentProducer) {
        this.paymentRepository = paymentRepository;
        this.paymentProducer = paymentProducer;
    }

    @Transactional
    public void save(Payment payment) {
        paymentRepository.save(toPaymentEntity(payment));
        paymentProducer.sendPayment(payment);
    }

    private PaymentEntity toPaymentEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.id())
                .payer(PayerEntity.builder()
                        .name("full name")
                        .build())
                .payee(PayeeEntity.builder()
                        .accountId(payment.payee().accountId())
                        .id(payment.payee().userId())
                        .build())
                .amount(payment.amount())
                .build();
    }
}
