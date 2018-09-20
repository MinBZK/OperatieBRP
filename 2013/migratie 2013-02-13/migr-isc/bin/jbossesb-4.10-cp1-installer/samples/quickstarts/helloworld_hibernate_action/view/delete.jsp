<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Transaction" %>
<%@ page import="org.jboss.soa.esb.samples.quickstart.hibernateaction.*" %>
<%
	long id = Long.parseLong(request.getParameter("id"));
	Transaction tx = null;
	Session sess = SessionFactorySingleton.getInstance().getCurrentSession();
	try {
		tx = sess.beginTransaction();
		Object order = sess.load (Order.class, new Long(id));
		sess.delete(order);
		tx.commit();
	} catch (Exception e) {
		e.printStackTrace();
		if (tx != null && tx.isActive()) {
			tx.rollback();
		}	
	}
	response.sendRedirect(request.getContextPath() + "/list.jsp");	
		
%>
