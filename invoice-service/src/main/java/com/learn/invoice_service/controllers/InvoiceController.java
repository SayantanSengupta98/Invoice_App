package com.learn.invoice_service.controllers;

import com.learn.invoice_service.dto.OrderDto;
import com.learn.invoice_service.dto.OrderResponseDto;
import com.learn.invoice_service.service.interfaces.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
@Slf4j
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;


    @GetMapping
    public String getInvoice() {
        return "success";
    }

    @PostMapping
    public OrderResponseDto getInvoice(@RequestBody OrderDto orderDto) {

        log.info( "Invoice generated for the customer " + orderDto.customerName());
        return invoiceService.generateInvoice(orderDto);
    }


}
