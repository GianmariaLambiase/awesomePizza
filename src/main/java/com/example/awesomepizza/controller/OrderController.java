package com.example.awesomepizza.controller;

import com.example.awesomepizza.enumerator.ORDER_STATUS;
import com.example.awesomepizza.dto.OrderDTO;
import com.example.awesomepizza.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO order) {
        return ResponseEntity.ok().body(this.orderService.create(order));
    }

    @GetMapping()
    public ResponseEntity<List<OrderDTO>> getAllOrders(@RequestParam(required = false) Set<ORDER_STATUS> status) {
        return ResponseEntity.ok().body(this.orderService.get(status));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<OrderDTO> get(@PathVariable UUID uuid) {
        return ResponseEntity.ok().body(this.orderService.get(uuid));
    }

    @PutMapping("/{uuid}/ready")
    public ResponseEntity<OrderDTO> setAsReady(@PathVariable UUID uuid) {
        return ResponseEntity.ok().body(this.orderService.setAsReady(uuid));
    }

    @PutMapping("/{uuid}/inProgress")
    public ResponseEntity<OrderDTO> setAsInProgress(@PathVariable UUID uuid) {
        return ResponseEntity.ok().body(this.orderService.setAsInProgress(uuid));
    }
}