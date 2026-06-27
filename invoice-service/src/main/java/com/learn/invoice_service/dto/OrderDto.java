package com.learn.invoice_service.dto;

import com.learn.invoice_service.models.Item;

import java.util.List;

public record OrderDto(String customerName, String customerNumber, List<Item> items, Double discount, Double totalAmount) {

}
