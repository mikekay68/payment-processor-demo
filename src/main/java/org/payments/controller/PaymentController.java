package org.payments.controller;

import org.payments.model.Payment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @PostMapping("/payment")
    public Payment createPayment(@RequestBody Payment payment) {
        return payment;
    }
}
