package com.example.demo.domain.order;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.order.request.NewOrderRequestDto;
import com.example.demo.global.dto.response.BackpacResponseBody;
import com.example.demo.global.dto.response.BackpacResponseData;
import com.example.demo.global.dto.response.DataResponseBody;
import com.example.demo.global.dto.response.ErrorData;
import com.example.demo.global.dto.response.SimpleResponseBody;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/order")
public class OrderCotroller {

    private OrderService service;
    
    public OrderCotroller(OrderService service) {
        this.service = service;
    }
    
    /**
     * 단일 회원 주문 목록 조회
     * @param request
     * @param accountId
     * @return
     */
    @ApiOperation(value="단일 회원 주문 목록", notes="단일 회원 주문 목록 조회 API 입니다.")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "accountId", value="계정ID", required=true, dataType = "int", paramType="path")
    })
    @GetMapping("/{accountId}")
    public BackpacResponseBody userOrder(HttpServletRequest request, @PathVariable long accountId) {
        
        if (request.getSession(false) == null) {
            return DataResponseBody.getFailBody("단일 회원 주문 목록 조회", ErrorData.getErrorData("로그인이 필요한 기능입니다."));
        }
        
        return DataResponseBody.getSuccessBody("단일 회원 주문 목록 조회", service.userOrder(accountId));
    }

    /**
     * 주문 하기
     * @param request
     * @param accountId
     * @param param
     * @return
     */
    @ApiOperation(value="주문 하기", notes="주문 API 입니다.")
    @ApiImplicitParams({
         @ApiImplicitParam(name = "accountId", value="계정ID", required=true, dataType = "int", paramType="path")
    })
    @PostMapping("/{accountId}")
    public BackpacResponseBody newOrder(HttpServletRequest request
                                      , @PathVariable long accountId
                                      , @RequestBody NewOrderRequestDto param) {
        
        if (request.getSession(false) == null) {
            return DataResponseBody.getFailBody("주문 하기", ErrorData.getErrorData("로그인이 필요한 기능입니다."));
        }
        
        BackpacResponseData response =  service.newOrder(accountId, param);
        
        if (response instanceof ErrorData) {
            return DataResponseBody.getFailBody("주문 하기", response);
        }
        else {
            return SimpleResponseBody.getSuccessBody("주문 하기");
        }
        
    }

    /**
     * 여러 회원 주문 정보 조회
     * @param request
     * @return
     */
    @ApiOperation(value="주문 하기", notes="주문 API 입니다.")
    @ApiImplicitParams({
           @ApiImplicitParam(name = "email", value="계정 이메일", required=true, dataType = "String", paramType="query")
         , @ApiImplicitParam(name = "nickname", value="계정 닉네임", required=true, dataType = "String", paramType="query")
         , @ApiImplicitParam(name = "pageCount", value="페이지당 게시글 수", required=false, defaultValue = "20", dataType = "String", paramType="query")
         , @ApiImplicitParam(name = "page", value="페이지 번호", required=false, defaultValue = "1", dataType = "String", paramType="query")
    })
    @GetMapping("/users")
    public BackpacResponseBody latelyOrders(HttpServletRequest request) {
        
        if (request.getSession(false) == null) {
            return DataResponseBody.getFailBody("회원 주문 목록 조회", ErrorData.getErrorData("로그인이 필요한 기능입니다."));
        }
        
        String pageCountStr = StringUtils.defaultString(request.getParameter("pageCount"), "20");
        String pageStr = StringUtils.defaultString(request.getParameter("page"), "1");
        
        // 페이지 파라미터 확인
        if (!pageCountStr.replaceAll("[0-9]", "").isBlank()
                || !pageStr.replaceAll("[0-9]", "").isBlank()) {
            return DataResponseBody.getFailBody("회원 주문 목록 조회", ErrorData.getErrorData("페이지는 숫자만 입력 가능합니다"));
        }
        
        // 조회에 필요한 파라미터
        String email = StringUtils.defaultString(request.getParameter("email"));
        String nickname = StringUtils.defaultString(request.getParameter("nickname"));
        int pageCount = Integer.parseInt(pageCountStr);
        int page = Integer.parseInt(pageStr);
        
        return DataResponseBody.getSuccessBody("회원 주문 목록 조회", service.latelyOrders(email, nickname, pageCount, page));
    }
    
}
