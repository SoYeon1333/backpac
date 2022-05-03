package com.example.demo.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.demo.domain.order.dto.LatelyOrderDto;
import com.example.demo.domain.order.dto.OrderDto;
import com.example.demo.domain.order.request.NewOrderRequestDto;
import com.example.demo.domain.order.response.LatelyOrderResponseDto;
import com.example.demo.domain.order.response.UserOrderResponseDto;
import com.example.demo.global.dto.response.BackpacResponseData;
import com.example.demo.global.dto.response.ErrorData;
import com.example.demo.global.dto.response.SuccessData;

@Service
public class OrderService {

    private OrderRepository repository;
    
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }
    
    /**
     * 단일 회원의 주문 목록 조회
     * @param accountId
     * @return
     */
    public BackpacResponseData userOrder(long accountId) {
        
        List<OrderEntity> rawOrders = repository.findByAccountId(accountId);
        
        List<OrderDto> orders = new ArrayList<>();
        rawOrders.stream().forEach(s -> {
            OrderDto order = new OrderDto();
            order.setOrderNo(s.getOrderNo());
            order.setProductName(s.getProductName());;
            order.setPaymentAt(s.getPaymentAt().toString());
            orders.add(order);
        });
        
        UserOrderResponseDto responseDto = new UserOrderResponseDto();
        responseDto.setAccountId(accountId);
        responseDto.setOrders(orders);
        
        return responseDto;
    }
    
    /**
     * 주문 하기
     * @param accountId
     * @param param
     * @return
     */
    public BackpacResponseData newOrder(long accountId, NewOrderRequestDto param) {

        String orderNo = this.getOrderNo();
        
        do {
            List<Object[]> checkNewOrder = repository.checkNewOrder(accountId, orderNo);
            if (checkNewOrder.size() == 0) {
                return ErrorData.getErrorData("존재하지 않는 계정입니다");
            }
            else if (checkNewOrder.get(0) != null) {
                orderNo = this.getOrderNo();
            }
            else {
                break;
            }
             
        } while (true);
        
        OrderEntity order = new OrderEntity();
        order.setAccountId(accountId);
        order.setOrderNo(orderNo);
        order.setPaymentAt(LocalDateTime.now());
        order.setProductName(param.getProductName());
        repository.save(order);
        
        return SuccessData.getSuccessData("");
    }

    /**
     * 여러 회원 주문 정보 조회
     * @param request
     * @return
     */
    public BackpacResponseData latelyOrders(String email, String nickname, int pageCount, int page) {
        
        List<Object[]> rawOrder = repository.findlatelyOrders(email, nickname);
        
        List<LatelyOrderDto> orders = new ArrayList<>();
        int offset = pageCount * (page - 1);
        rawOrder.stream().skip(offset).limit(pageCount).forEach(s -> {
            LatelyOrderDto latelyOrder = LatelyOrderDto.builder().orderId((long)s[0])
                                                                 .accountId((long)s[1])
                                                                 .orderNo((String)s[2])
                                                                 .productName((String)s[3])
                                                                 .paymentAt(((LocalDateTime)s[4]).toString())
                                                                 .nickname((String)s[5])
                                                                 .email((String)s[6])
                                                       .build();
            
            orders.add(latelyOrder);
        });
        
        LatelyOrderResponseDto response = new LatelyOrderResponseDto();
        response.setOrders(orders);
        
        return response;
    }
    
    /**
     * 주문 일련번호 생성 하기
     * @return
     */
    private String getOrderNo() {
        
        String baseChar = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder serial = new StringBuilder();
        Random random = new Random();
        
        int maxIndex = baseChar.length() - 1;
        for (int i = 0; i < 12; i ++) {
            serial.append(baseChar.charAt(random.nextInt(maxIndex)));
        }
        
        return serial.toString();
    }
    
}
