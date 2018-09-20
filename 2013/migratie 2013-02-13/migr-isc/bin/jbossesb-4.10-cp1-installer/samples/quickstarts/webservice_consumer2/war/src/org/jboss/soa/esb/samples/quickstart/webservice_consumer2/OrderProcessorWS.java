/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and others contributors as indicated 
 * by the @authors tag. All rights reserved. 
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors. 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 * 
 * (C) 2005-2006,
 * @author JBoss Inc.
 */
package org.jboss.soa.esb.samples.quickstart.webservice_consumer2;

import org.jboss.soa.esb.samples.quickstart.webservice_consumer2.Order;
import org.jboss.soa.esb.samples.quickstart.webservice_consumer2.OrderStatus;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.WebParam;

@WebService(name = "OrderProcessor", targetNamespace = "http://webservice_consumer2/orderProcessor")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class OrderProcessorWS
{
   @WebMethod
   public OrderStatus processOrder(@WebParam(name = "order") Order order)
   {
	  OrderStatus status = new OrderStatus();
	  System.out.println("Order is: " + order.toString());
     
	  status.setComment("order processed");
	  status.setId(order.getId());
	  status.setReturnCode(OrderStatus.SUCCESS);
	  
      return status;

   }
}
