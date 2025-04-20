package com.example.tseytwa.CreateOrderMicroservice.dao.repository;

import com.example.tseytwa.CreateOrderMicroservice.dao.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByCustomerId(String id);

    List<Order> findAllByCustomerId(String id);
}
