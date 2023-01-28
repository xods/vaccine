package com.vaccines.vaccine.service;

import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findByStatusOrStatus(EStatus status, EStatus status1);
    Optional<Order> findById(Long id);
    Order save(Order order);
}
