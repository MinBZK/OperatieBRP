<html>
<body>
<%
	String product = request.getParameter("product");
	String quantString = request.getParameter("quantity");
	String priceString = request.getParameter("price");

	int quantity = Integer.parseInt(quantString);
	float price = Float.parseFloat(priceString);
%>
Product: <%=product%><p>
Quantity: <%=quantity%><p>
Price: <%=price%><p>
</body>
</html>
