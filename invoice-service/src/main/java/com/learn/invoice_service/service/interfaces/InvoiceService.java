package com.learn.invoice_service.service.interfaces;

import com.learn.invoice_service.dto.OrderDto;
import com.learn.invoice_service.dto.OrderResponseDto;

public interface InvoiceService
{
  OrderResponseDto generateInvoice(OrderDto orderDto);
}
