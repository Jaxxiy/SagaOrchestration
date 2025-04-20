package com.example.tseytwa.CreateOrderMicroservice.dao.entity;

import jakarta.persistence.*;

@Entity(name = '"'+"order"+'"')
public class Order {
    @Id
    public String id;

    @Column(name = "doctor_surname")
    public String doctorSurname;

    @Column(name = "pay_way")
    public String payWay;

    @Column(name = "price")
    public Integer price;

    @Column(name = "customer_id")
    public String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorSurname() {
        return doctorSurname;
    }

    public void setDoctorSurname(String doctorSurname) {
        this.doctorSurname = doctorSurname;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
}
