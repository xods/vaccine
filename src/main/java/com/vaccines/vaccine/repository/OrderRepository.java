package com.vaccines.vaccine.repository;

import com.vaccines.vaccine.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("order")
public interface OrderRepository extends JpaRepository<Order, Long> {
}