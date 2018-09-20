
<items orderId="${Order.@orderId}" date="${Order.@orderDate}" customer="${Order.Customer.@userName}">
<#foreach orderLine in OrderLines.OrderLine>
    <item id="${orderLine.Product.@productId}" price="${orderLine.Product.@price}"/>
</#foreach>
</items>