package com.example.demo.domain.order.response;

import java.util.List;

import com.example.demo.domain.order.dto.OrderDto;
import com.example.demo.global.dto.response.BackpacResponseData;

import lombok.Data;

@Data
public class UserOrderResponseDto implements BackpacResponseData {

    private long accountId;
    private List<OrderDto> orders;
}
