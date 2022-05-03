package com.example.demo.domain.account.response;

import com.example.demo.global.dto.response.BackpacResponseData;

import lombok.Data;

@Data
public class MeResponseDto implements BackpacResponseData {

    private String name;
    private String nickname;
    private String phone;
    private String email;
    private String sex;
    private String type;
    
}
