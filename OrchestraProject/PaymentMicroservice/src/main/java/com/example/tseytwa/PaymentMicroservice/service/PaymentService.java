package com.example.tseytwa.PaymentMicroservice.service;

import com.example.tseytwa.PaymentMicroservice.dao.entity.Payment;
import com.example.tseytwa.PaymentMicroservice.dao.repository.PaymentRepository;
import com.example.tseytwa.core.exception.NotEnoughMoneyException;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void paymentCommand(String id, int balance) {
        if (!checkCustomer(id)){
            Payment payment = new Payment();
            payment.setId(id);
            payment.setBalance(1000);
            paymentRepository.save(payment);
        }
        Payment payment = paymentRepository.findById(id).get();
        if (payment.getBalance() < balance){
            throw new NotEnoughMoneyException();
        }
        payment.setBalance(payment.getBalance() - balance);
        paymentRepository.save(payment);
    }

    private boolean checkCustomer(String customerId) {
        return paymentRepository.findById(customerId).isPresent();
    }
}
