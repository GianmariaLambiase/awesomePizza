
package com.example.awesomepizza.dao;

import com.example.awesomepizza.entity.OrderItem;
import com.example.awesomepizza.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class OrderItemDAO {

    private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemDAO(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }


    public void saveAll(ArrayList<OrderItem> orderItemList) {
        this.orderItemRepository.saveAll(orderItemList);
    }

    public void save(OrderItem orderItem) {
        this.orderItemRepository.save(orderItem);
    }
}
