package com.learn.invoice_service.service.interfaces;

import com.learn.invoice_service.dto.OrderDto;

public interface PdfService {

    String getPdfInBase64 (OrderDto orderDto, String orderId);
}
