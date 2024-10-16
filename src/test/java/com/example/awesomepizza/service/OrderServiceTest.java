package com.example.awesomepizza.service;

import com.example.awesomepizza.dto.OrderDTO;
import com.example.awesomepizza.dto.OrderItemDTO;
import com.example.awesomepizza.dto.ProductDTO;
import com.example.awesomepizza.entity.Order;
import com.example.awesomepizza.entity.OrderItem;
import com.example.awesomepizza.entity.Product;
import com.example.awesomepizza.enumerator.ORDER_STATUS;
import com.example.awesomepizza.repository.OrderItemRepository;
import com.example.awesomepizza.repository.OrderRepository;
import com.example.awesomepizza.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Usa il profilo di test con H2
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;
    

    Order initOrder(ORDER_STATUS orderStatus) {
        // Prepara i dati di test
        Optional<Product> product = productRepository.findAll().stream().findAny();
        assertTrue(product.isPresent());

        // Crea l'ordine con un OrderItem
        Order order = new Order();
        order.setCustomerName("gianmaria lambiase");
        order.setCustomerEmail("gianmaria.lambiase@gmail.com");
        order.setStatus(orderStatus);

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product.get());
        orderItem.setAmount(3.0);
        orderItem.setNote("order item note");

        Set<OrderItem> orderItemSet = new HashSet<>();
        orderItemSet.add(orderItem);
        order.setOrderItemSet(orderItemSet);
        order = orderRepository.save(order);
        orderItem.setOrder(order);
        orderItemRepository.save(orderItem);
        return order;
    }

    @Test
    @Transactional
    void testCreateOrder() {
        // Crea un nuovo OrderDTO
        Optional<Product> product = productRepository.findAll().stream().findAny();
        assertTrue(product.isPresent());

        ProductDTO productDTO = new ProductDTO();
        productDTO.setUuid(product.get().getUuid());

        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProduct(productDTO);
        orderItemDTO.setAmount(2.0);
        orderItemDTO.setNote("new item note");

        Set<OrderItemDTO> orderItemDTOSet = new HashSet<>();
        orderItemDTOSet.add(orderItemDTO);

        OrderDTO newOrderDTO = new OrderDTO();
        newOrderDTO.setUuid(UUID.randomUUID());
        newOrderDTO.setCustomerName("Mario Rossi");
        newOrderDTO.setCustomerEmail("mario.rossi@example.com");
        newOrderDTO.setNote("new order note");
        newOrderDTO.setStatus(ORDER_STATUS.PENDING);
        newOrderDTO.setOrderItemSet(orderItemDTOSet);

        OrderDTO savedOrderDTO = orderService.create(newOrderDTO);
        assertNotNull(savedOrderDTO.getUuid());

        Optional<Order> fetchedOrder = orderRepository.findById(savedOrderDTO.getUuid());
        assertTrue(fetchedOrder.isPresent());
        assertEquals("Mario Rossi", fetchedOrder.get().getCustomerName());
    }

    @Test
    @Transactional
    void testGetOrderById() {
        Order persistedOrder = initOrder(ORDER_STATUS.PENDING);

        OrderDTO fetchedOrderDTO = orderService.get(persistedOrder.getUuid());
        assertNotNull(fetchedOrderDTO);
        assertEquals("gianmaria lambiase", fetchedOrderDTO.getCustomerName());
        assertEquals(1, fetchedOrderDTO.getOrderItemSet().size());
    }

    @Test
    @Transactional
    void testSetAsReady() {

        Order persistedOrder = initOrder(ORDER_STATUS.IN_PROGRESS);
        // Modifica lo stato dell'ordine a "READY"
        OrderDTO updatedOrderDTO = orderService.setAsReady(persistedOrder.getUuid());
        assertEquals(ORDER_STATUS.READY, updatedOrderDTO.getStatus());

        // Verifica che lo stato dell'ordine sia stato aggiornato nel database
        Optional<Order> fetchedOrder = orderRepository.findById(persistedOrder.getUuid());
        assertTrue(fetchedOrder.isPresent());
        assertEquals(ORDER_STATUS.READY, fetchedOrder.get().getStatus());
    }


    @Test
    @Transactional
    void testSetAsInProgress() {

        Order persistedOrder = initOrder(ORDER_STATUS.PENDING);
        // Modifica lo stato dell'ordine a "READY"
        OrderDTO updatedOrderDTO = orderService.setAsInProgress(persistedOrder.getUuid());
        assertEquals(ORDER_STATUS.IN_PROGRESS, updatedOrderDTO.getStatus());

        // Verifica che lo stato dell'ordine sia stato aggiornato nel database
        Optional<Order> fetchedOrder = orderRepository.findById(persistedOrder.getUuid());
        assertTrue(fetchedOrder.isPresent());
        assertEquals(ORDER_STATUS.IN_PROGRESS, fetchedOrder.get().getStatus());
    }
}
