package com.vaccines.vaccine.service;

import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findByStatusOrStatus(EStatus status, EStatus status2);
    List<Order> findByKreator_IdAndStatusOrStatus(Long id, EStatus status, EStatus status2);
    List<Order> findByKreator_IdAndStatus(Long id, EStatus status);
    Optional<Order> findById(Long id);
    Order save(Order order);
}
