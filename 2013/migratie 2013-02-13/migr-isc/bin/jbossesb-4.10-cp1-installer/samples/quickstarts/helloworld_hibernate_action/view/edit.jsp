<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.Transaction" %>
<%@ page import="org.jboss.soa.esb.samples.quickstart.hibernateaction.*" %>
<html>
<head>
<title>Hibernate Listener Example</title>
<link rel="shortcut icon" href="http://www.jboss.com/favicon.ico"/>
<link rel="stylesheet" href="http://www.jboss.com/themes/jbosstheme/style/custom.css" type="text/css" media="all">
<link rel="stylesheet" href="http://www.jboss.com/themes/jbosstheme/style/global.css" type="text/css" media="all">
<link rel="stylesheet" href="http://www.jboss.com/themes/jbosstheme/style/headings.css" type="text/css" media="all">
<link rel="stylesheet" href="http://www.jboss.com/themes/jbosstheme/style/layout.css" type="text/css" media="all">
<link rel="stylesheet" href="http://www.jboss.com/themes/jbosstheme/style/navigation.css" type="text/css" media="all">
<link rel="stylesheet" href="http://www.jboss.com/themes/jbosstheme/style/pagelayout.css" type="text/css" media="all">
<link rel="stylesheet" href="http://www.jboss.com/themes/jbosstheme/style/tables.css" type="text/css" media="all">
<link rel="stylesheet" href="http://www.jboss.com/themes/jbosstheme/style/screen.css" type="text/css" media="screen">
<link rel="stylesheet" href="http://www.jboss.com/themes/jbosstheme/style/print.css" type="text/css" media="print">
<link rel="stylesheet" href="http://www.jboss.com/themes/jbosstheme/style/common.css" type="text/css" media="all">
</head>
<%
	long id = Long.parseLong(request.getParameter("id"));
	Order order = null;
	Transaction tx = null;
	Session sess = SessionFactorySingleton.getInstance().getCurrentSession();

	try {
		tx = sess.beginTransaction();
		order = (Order) sess.load (Order.class, new Long(id));

%>

<body>
	<form id="orderRequest" action="update.jsp" method="post">
	<table width="100%" border="0" cellpadding="10" cellspacing="3">
		<tr><td>Product</td><td><input type="text" 
			value="<%=order.getProduct()%>"
			name="product" id="product"/></td></tr>
		<tr><td>Quantity</td><td><input type="text" 
			value="<%=order.getQuantity()%>" 
			name="quantity" id="quantity"/></td></tr>
		<tr><td>Price</td><td><input type="text" 
			value="<%=order.getPrice()%>" 
			name="price" id="price"/></td></tr>
		<input type="hidden" value="<%=id%>" name="id" id="id"/>
		<tr><td><input type="submit" value="submit"></td></tr>
	</table>
	</form>
</body>
</html>
<%
		tx.commit();
	} catch (Exception e) {
		e.printStackTrace();
	}
%>


