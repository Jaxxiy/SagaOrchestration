package com.example.tseytwa.PaymentMicroservice.dao.repository;

import com.example.tseytwa.PaymentMicroservice.dao.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
}
