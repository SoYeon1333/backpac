package com.example.demo.domain.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    
    public List<OrderEntity> findByAccountId(long accountId);
    
    @Query(value = 
       "SELECT po.id"
          + ", po.accountId"
          + ", po.orderNo "
          + ", po.productName "
          + ", po.paymentAt "
          + ", ua.nickname "
          + ", ua.email "
      + "FROM OrderEntity po "
     + "INNER JOIN AccountEntity ua "
        + "ON ua.id = po.accountId "
       + "AND ua.email LIKE CONCAT('%', :email , '%') "
       + "AND ua.nickname LIKE CONCAT('%', :nickname , '%') "
     + "WHERE po.paymentAt = (SELECT MAX(po2.paymentAt) "
                              + "FROM OrderEntity po2 "
                             + "WHERE po2.accountId = po.accountId) "
     + "GROUP BY po.accountId "
     + "ORDER BY po.accountId ASC "
    )
    public List<Object[]> findlatelyOrders(@Param("email") String email, @Param("nickname") String nickname);
    
    @Query(value = 
        "SELECT po.orderNo "
        + "FROM AccountEntity ua "
        + "LEFT JOIN OrderEntity po "
        + "  ON ua.id = po.accountId "
        + " AND po.orderNo = :orderNo "
       + "WHERE ua.id = :accountId"
    )
    public List<Object[]> checkNewOrder(@Param("accountId") long accountId, @Param("orderNo") String orderNo);
}
