package com.learn.invoice_service.service.impl;

import com.learn.invoice_service.dto.OrderDto;
import com.learn.invoice_service.dto.OrderResponseDto;
import com.learn.invoice_service.service.interfaces.InvoiceService;
import com.learn.invoice_service.service.interfaces.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private  final PdfService pdfService;

    @Override
    public OrderResponseDto generateInvoice(OrderDto orderDto ) {


        String orderId = UUID.randomUUID().toString();
        String base64Pdf = pdfService.getPdfInBase64(orderDto, orderId);

        return new OrderResponseDto(orderId,base64Pdf);
    }
}
