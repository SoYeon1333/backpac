package com.example.demo.domain.order.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LatelyOrderDto {

    private long orderId;
    private long accountId;
    private String orderNo;
    private String productName;
    private LocalDateTime paymentAt;
    private String nickname;
    private String email;
    
}
