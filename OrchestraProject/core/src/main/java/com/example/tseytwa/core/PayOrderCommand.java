package com.example.tseytwa.core;

public class PayOrderCommand {
    private String orderId;
    private String payWay;
    private String doctorSurname;
    private String customerId;
    private Integer price;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getDoctorSurname() {
        return doctorSurname;
    }

    public void setDoctorSurname(String doctorSurname) {
        this.doctorSurname = doctorSurname;
    }

    @Override
    public String toString() {
        return "PayOrderCommand{" +
                "orderId='" + orderId + '\'' +
                ", payWay='" + payWay + '\'' +
                ", doctorSurname='" + doctorSurname + '\'' +
                ", customerId='" + customerId + '\'' +
                ", price=" + price +
                '}';
    }
}
