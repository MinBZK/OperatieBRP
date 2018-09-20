<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Transaction" %>
<%@ page import="org.jboss.soa.esb.samples.quickstart.hibernateaction.*" %>
<html>
<body>
<%
	String product = request.getParameter("product");
	String quantString = request.getParameter("quantity");
	String priceString = request.getParameter("price");

	int quantity = Integer.parseInt(quantString);
	float price = Float.parseFloat(priceString);

	Order insOrder = new Order(product, new Integer(quantity),
		new Float(price));
	Transaction tx = null;
	Session sess = SessionFactorySingleton.getInstance().openSession();
	try {
		tx = sess.beginTransaction();
		sess.save(insOrder);
		tx.commit();
		sess.flush();
	} catch (Exception e) {
		e.printStackTrace();
		if (tx != null && tx.isActive()) {
			tx.rollback();
		}
	}	

	response.sendRedirect(request.getContextPath() + "/list.jsp");	
%>
Product: <%=product%><p>
Quantity: <%=quantity%><p>
Price: <%=price%><p>
</body>
</html>
