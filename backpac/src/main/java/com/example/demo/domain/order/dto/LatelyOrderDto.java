package com.example.demo.domain.order.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LatelyOrderDto {

    private long orderId;
    private long accountId;
    private String orderNo;
    private String productName;
    private String paymentAt;
    private String nickname;
    private String email;
    
}
