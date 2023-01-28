package com.vaccines.vaccine.service.implementation;

import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.Order;
import com.vaccines.vaccine.repository.OrderRepository;
import com.vaccines.vaccine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository repository;

    @Override
    public List<Order> findByStatusOrStatus(EStatus status, EStatus status1) {
        return repository.findByStatusOrStatus(status, status1);
    }

    public Optional<Order> findById(Long id){
        return repository.findById(id);
    }

    public Order save(Order order){
        return repository.save(order);
    }
}
