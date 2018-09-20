<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Transaction" %>
<%@ page import="org.jboss.soa.esb.samples.quickstart.hibernateaction.*" %>
<%
	String product = request.getParameter("product");
	String quantString = request.getParameter("quantity");
	String priceString = request.getParameter("price");
	String idString = request.getParameter("id");
	
	long id = Long.parseLong(idString);
	int quantity = Integer.parseInt(quantString);
	float price = Float.parseFloat(priceString);
	
	Transaction tx = null;
	Session sess = SessionFactorySingleton.getInstance().getCurrentSession();
	try {
		tx = sess.beginTransaction();
		Order order = (Order) sess.get(Order.class, new Long(id));
		order.setProduct(product);
		order.setQuantity(new Integer(quantity));
		order.setPrice(new Float(price));
		tx.commit();
	} catch (Exception e) {
		e.printStackTrace();
		if (tx != null && tx.isActive()) {
			tx.rollback();
		}
	} finally {
		if (sess.isOpen()) {
			sess.close();
		}
	}

	response.sendRedirect(request.getContextPath() + "/list.jsp");	
%>
