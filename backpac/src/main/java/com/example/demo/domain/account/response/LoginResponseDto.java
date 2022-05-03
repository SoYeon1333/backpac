package com.example.demo.domain.account.response;

import com.example.demo.global.dto.response.BackpacResponseData;

import lombok.Data;

@Data
public class LoginResponseDto implements BackpacResponseData {

    private long accountId;
    
}
