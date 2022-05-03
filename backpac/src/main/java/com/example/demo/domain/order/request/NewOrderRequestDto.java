package com.example.demo.domain.order.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class NewOrderRequestDto {

    @NotBlank
    @NotEmpty
    private String productName;
    
}
