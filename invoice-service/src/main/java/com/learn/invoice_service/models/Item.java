package com.learn.invoice_service.models;

public record Item(String description, Double quantity, Double unitPrice, Double totalPrice) {
}
