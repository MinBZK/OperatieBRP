<orderitem id="${orderItem.itemId}" order="${header.orderId}">
    <customer>
        <name>${header.customerName}</name>
        <number>${header.customerNumber}</number>
    </customer>
    <details>
        <productId>${orderItem.productId}</productId>
        <quantity>${orderItem.quantity}</quantity>
        <price>${orderItem.price}</price>
    </details>
</orderitem>