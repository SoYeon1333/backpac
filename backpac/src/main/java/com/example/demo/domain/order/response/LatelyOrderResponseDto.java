package com.example.demo.domain.order.response;

import java.util.List;

import com.example.demo.domain.order.dto.LatelyOrderDto;
import com.example.demo.global.dto.response.BackpacResponseData;

import lombok.Data;

@Data
public class LatelyOrderResponseDto implements BackpacResponseData {

    private List<LatelyOrderDto> orders;
    
}
