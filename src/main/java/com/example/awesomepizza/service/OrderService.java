package com.example.awesomepizza.service;

import com.example.awesomepizza.enumerator.ORDER_STATUS;
import com.example.awesomepizza.converter.OrderConverter;
import com.example.awesomepizza.dao.OrderDAO;
import com.example.awesomepizza.dao.OrderItemDAO;
import com.example.awesomepizza.dao.ProductDAO;
import com.example.awesomepizza.dto.OrderDTO;
import com.example.awesomepizza.entity.Order;
import com.example.awesomepizza.entity.OrderItem;
import com.example.awesomepizza.entity.Product;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;
import java.util.*;

@Component
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    ProductDAO productDao;

    OrderDAO orderDao;

    OrderItemDAO orderItemDao;

    @Autowired
    public OrderService(ProductDAO productDao, OrderDAO orderDao, OrderItemDAO orderItemDao) {
        this.productDao = productDao;
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
    }

    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        Order order = this.orderDao.saveAndFlush(OrderConverter.convertAsEntity(orderDTO));
        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderDTO.getOrderItemSet().stream().forEach(orderItemDTO -> {

            Optional<Product> product = this.productDao.getById(orderItemDTO.getProduct().getUuid());
            if (orderItemDTO.getProduct() != null && !product.isPresent()) {
                log.debug(MessageFormat.format("Product {0} NOT FOUND", orderItemDTO.getProduct().getUuid()));
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,MessageFormat.format("Product {0} NOT FOUND", orderItemDTO.getProduct().getUuid()));
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setAmount(orderItemDTO.getAmount());
            orderItem.setNote(orderItemDTO.getNote());
            orderItem.setProduct(product.get());
            orderItem.setOrder(order);
            orderItemList.add(orderItem);
        });
        this.orderItemDao.saveAll(orderItemList);

        return OrderConverter.convertAsDTO(order);
    }

    public List<OrderDTO> get(Set<ORDER_STATUS> status) {
        return OrderConverter.convertAsDTO(this.orderDao.getAll(status));
    }

    public OrderDTO get(UUID uuid) {
        return OrderConverter.convertAsDTO(this.orderDao.getById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public OrderDTO setAsReady(UUID uuid) {
        Order order = this.orderDao.getById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (order.getStatus() != ORDER_STATUS.IN_PROGRESS) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,MessageFormat.format("Order status: {0}, must bet {1}", order.getStatus(),ORDER_STATUS.IN_PROGRESS));
        }
        order.setToReady();
        return OrderConverter.convertAsDTO(this.orderDao.save(order));
    }

    public OrderDTO setAsInProgress(UUID uuid) {
        Order order = this.orderDao.getById(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (order.getStatus() != ORDER_STATUS.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,MessageFormat.format("Order status: {0}, must bet {1}", order.getStatus(),ORDER_STATUS.PENDING));
        }
        order.setToInProgress();
        return OrderConverter.convertAsDTO(this.orderDao.save(order));
    }
}
