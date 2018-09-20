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
<body>
	<form id="orderRequest" action="/processorder" method="post">
	<table width="100%" border="0" cellpadding="10" cellspacing="3">
		<tr>
			<td>Product</td>
			<td>Quantity</td>
			<td>Price</td>
			<td>Action</td>
		</tr>
		<%
		Transaction tx = null;
		Session sess = SessionFactorySingleton.getInstance().openSession();
	
		try {
			tx = sess.beginTransaction();
			List records = sess.createQuery("select o from Order as o").list();
			for (Iterator iter = records.iterator(); iter.hasNext();) {
				Order order = (Order) iter.next();
				if (order != null) {
			%>
		<tr>	
			<td><%=order.getProduct()%></td>
			<td><%=order.getQuantity()%></td>
			<td><%=order.getPrice()%></td>
			<td><a href="<%=request.getContextPath() + "/edit.jsp"%>?id=<%=order.getId()%>">Edit</a> | 
				<a href="<%=request.getContextPath() + "/delete.jsp"%>?id=<%=order.getId()%>">Delete</a></td>
		</tr>
			<%
				}
			}
		} catch (Exception e) {
			e.printStackTrace();	
		}
		%>
		<tr><td colspan="3"><a href="<%=request.getContextPath() + "/index.jsp"%>">Return to Entry Page</a></td>
	</table>
</body>
</html>
