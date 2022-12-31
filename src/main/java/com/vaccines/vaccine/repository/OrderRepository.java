package com.vaccines.vaccine.repository;

import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("order")
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatusOrStatus(EStatus status, EStatus status1);
    
}