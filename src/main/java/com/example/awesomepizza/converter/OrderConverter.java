package com.example.awesomepizza.converter;

import com.example.awesomepizza.dto.OrderDTO;
import com.example.awesomepizza.entity.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {

    public static Order convertAsEntity(OrderDTO from) {
        Order to = new Order();
        to.setNote(from.getNote());
        to.setCustomerName(from.getCustomerName());

        return to;
    }

    public static OrderDTO convertAsDTO(Order from) {
        OrderDTO to = new OrderDTO();
        to.setStatus(from.getStatus());
        to.setUuid(from.getUuid());
        to.setCustomerName(from.getCustomerName());
        to.setNote(from.getNote());
        to.setCustomerName(from.getCustomerName());
        to.setCustomerEmail(from.getCustomerEmail());
        to.setOrderItemSet(OrderItemConverter.convertAsDTO(from.getOrderItemSet()));
        to.setCreatedAt(from.getCreatedAt());
        return to;
    }

    public static List<OrderDTO> convertAsDTO(List<Order> orderList) {
        return orderList.stream().map(OrderConverter::convertAsDTO).collect(Collectors.toList());
    }
}
