package com.learn.invoice_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponseDto {
    String orderId;
    String base64Pdf;
}
