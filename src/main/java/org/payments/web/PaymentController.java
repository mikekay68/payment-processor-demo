package org.payments.web;

import lombok.extern.slf4j.Slf4j;
import org.payments.model.Payment;
import org.payments.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        log.info("Starting PaymentController");
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        paymentService.save(payment);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }
}
