package com.example.demo.domain.order.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderDto {

    private String orderNo;
    private String productName;
    private LocalDateTime paymentAt;
    
}
