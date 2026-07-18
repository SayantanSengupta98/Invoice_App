package com.learn.user_service.util;

public interface Constants {

     String SUCCESS = "Success";
     String FAILED = "Failed";

     String TEMPLATE_PROMPT = """
             Generate an elegant invoice template using Thymeleaf.
             
             Return only HTML.
             
             Requirements:
             
             - Valid HTML5
             - XHTML compatible
             - No markdown
             - No explanation
             - No JavaScript
             - Inline CSS only
             - A4 page
             - No notes or amount due message should be added.
             - No $ symbol needed
             
             Variables:
             
             ${shopName}
             ${customerName}
             ${customerNumber}
             ${orderId}
             ${orderDate}
             ${discount}
             ${totalAmount}
             
             Items collection:
             
             <tr th:each="item : ${items}">
             
             Columns:
             
             ${item.name}
             ${item.quantity}
             ${item.price}
             ${item.total}
             
             Style:
             
             - Soft Professional colors
             - Light colored sections
             - Modern typography
             - Premium invoice layout
             - Beautiful spacing
             - Suitable for printing
             """;


}
