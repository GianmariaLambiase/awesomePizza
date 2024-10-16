package com.example.awesomepizza.dao;

import com.example.awesomepizza.enumerator.ORDER_STATUS;
import com.example.awesomepizza.entity.Order;
import com.example.awesomepizza.entity.specification.OrderSpecifications;
import com.example.awesomepizza.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class OrderDAO {

    private OrderRepository orderRepository;

    @Autowired
    public OrderDAO(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order saveAndFlush(Order order) {
        return orderRepository.saveAndFlush(order);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAll(Set<ORDER_STATUS> status) {
        return this.orderRepository.findAll(OrderSpecifications.hasStatusInSet(status));
    }

    public Optional<Order> getById(UUID uuid) {
        return this.orderRepository.findById(uuid);
    }
}
