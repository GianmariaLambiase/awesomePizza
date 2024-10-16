package com.example.awesomepizza.converter;

import com.example.awesomepizza.dto.OrderItemDTO;
import com.example.awesomepizza.entity.OrderItem;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderItemConverter {

    public static Set<OrderItemDTO> convertAsDTO(Set<OrderItem> orderItemList) {
        return orderItemList == null ? Collections.emptySet() : orderItemList.stream().map(OrderItemConverter::convertAsDTO).collect(Collectors.toSet());
    }

    public static OrderItemDTO convertAsDTO(OrderItem from) {
        OrderItemDTO to = new OrderItemDTO();
        to.setNote(from.getNote());
        to.setUuid(from.getUuid());
        to.setAmount(from.getAmount());
        to.setProduct(ProductConverter.convertAsDTO(from.getProduct()));
        return to;
    }
}
