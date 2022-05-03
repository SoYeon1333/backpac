package com.example.demo.domain.order;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "product_order")
public class OrderEntity {

    @Id
    private long id;
    private long accountId;
    private String orderNo;
    private String productName;
    private LocalDateTime paymentAt;
    
}
