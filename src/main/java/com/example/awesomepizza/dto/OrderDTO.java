package com.example.awesomepizza.dto;

import com.example.awesomepizza.enumerator.ORDER_STATUS;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class OrderDTO {

    private UUID uuid;

    @NotBlank
    private String customerName;

    @NotBlank
    @Email
    private String customerEmail;

    private String note;

    private ORDER_STATUS status;

    @NotEmpty
    private Set<OrderItemDTO> orderItemSet;
    
    private LocalDateTime createdAt;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ORDER_STATUS getStatus() {
        return status;
    }

    public void setStatus(ORDER_STATUS status) {
        this.status = status;
    }

    public Set<OrderItemDTO> getOrderItemSet() {
        return orderItemSet;
    }

    public void setOrderItemSet(Set<OrderItemDTO> orderItemSet) {
        this.orderItemSet = orderItemSet;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
