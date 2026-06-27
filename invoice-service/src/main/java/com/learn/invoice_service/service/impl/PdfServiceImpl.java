package com.learn.invoice_service.service.impl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.learn.invoice_service.dto.OrderDto;
import com.learn.invoice_service.service.interfaces.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.util.Base64;


@Service
@RequiredArgsConstructor
public class PdfServiceImpl  implements PdfService {

    private final SpringTemplateEngine springTemplateEngine;


    @Override
    public String getPdfInBase64(OrderDto order, String orderId) {

        Context context = new Context();

        // Shop Details
        context.setVariable("shopName", "HardCoded for now");

        // Customer Details
        context.setVariable("customerName", order.customerName());
        context.setVariable("customerNumber", order.customerNumber());

        // Order Details
        context.setVariable("orderId",orderId);

        // Items
        context.setVariable("items", order.items());

        // Total
        context.setVariable("totalAmount", order.totalAmount());

        String html = springTemplateEngine.process("invoice", context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ConverterProperties properties = new ConverterProperties();

        HtmlConverter.convertToPdf(
                html,
                outputStream,
                properties
        );

        byte[] byteArray = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(byteArray);
    }
}
