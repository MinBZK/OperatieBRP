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
package org.jboss.soa.esb.samples.quickstart.spring_jpetstore;

import org.jboss.soa.esb.actions.AbstractSpringAction;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

import org.springframework.samples.jpetstore.domain.logic.PetStoreFacade;
import org.springframework.samples.jpetstore.domain.Order;
import java.util.List;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Spring enabled action that makes a DB insert call to the 
 * Spring jpetstore sample application.
 * 
 * @see org.jboss.soa.esb.actions.AbstractSpringAction
 * @see applicationContext.xml
 * @see dataAccessContext-local.xml
 * 
 * @author <a href="mailto:james.williams@redhat.com">James Williams</a>.
 * 
 */
public class InsertOrderAction extends AbstractSpringAction
{
   public InsertOrderAction(ConfigTree config) throws Exception
   {
      configTree = config;
   }

   public Message insertOrder(Message message) throws Exception
   {
      Body msgBody = message.getBody();
      XStream xstream = new XStream(new DomDriver());
      xstream.alias("order", Order.class);
      Order order = (Order) xstream.fromXML((String) msgBody.get());

      PetStoreFacade petStore = (PetStoreFacade) getBeanFactory()
            .getBean("petStore");
      petStore.insertOrder(order);

      System.out.println("ORDER SUCCESSFULLY INSERTED\n");
      printOrderDetails(getOrderFromDB());

      return message;
   }

   private void printOrderDetails(Order order)
   {
      System.out.println("************************");
      System.out.println("TOTAL PRICE: " + order.getTotalPrice());
      System.out.println("SHIP TO:");
      System.out.println(order.getShipToFirstName() + " "
            + order.getShipToLastName());
      System.out.println(order.getShipAddress1() + " "
            + order.getShipAddress2());
      System.out.println(order.getShipCity() + " " + order.getShipZip());
      System.out.println("************************");
   }

   private Order getOrderFromDB() throws Exception
   {
      PetStoreFacade petStore = (PetStoreFacade) getBeanFactory()
            .getBean("petStore");
      System.out.println("QUERYING DATABASE FOR INSERTED ORDER");
      List<Order> orders = petStore.getOrdersByUsername("j2ee");
      return orders.get(orders.size() - 1);
   }

   // utility methods used to create/generate a test order pojo and xml

   // private Order getTestOrder()
   // {
   // Order order = new Order();
   //	   
   // order.setOrderDate(new Date());
   // order.setStatus("P");
   // order.setTotalPrice(22.22);
   // order.setUsername("j2ee");
   // order.setShipToFirstName("ABC");
   // order.setShipToLastName("XYX");
   // order.setShipState("CA");
   //	   
   // order.setShipToFirstName("James");
   // order.setShipToLastName("Williams");
   // order.setShipAddress1("123 Way");
   // order.setShipAddress2("hoaky drive");
   // order.setShipCity("Los Angelos");
   // order.setShipCountry("USA");
   // order.setShipZip("30253");
   //	    
   // order.setBillToFirstName("James");
   // order.setBillToLastName("Williams");
   // order.setBillAddress1("123 Way");
   // order.setBillAddress2("hoaky drive");
   // order.setBillCity("Los Angelos");
   // order.setBillCountry("USA");
   // order.setBillState("CA");
   // order.setBillZip("30253");
   //	   
   // order.setCreditCard("999 9999 9999 9999");
   // order.setExpiryDate("12/03");
   // order.setCardType("Visa");
   // order.setLocale("CA");
   // order.setCourier("UPS");
   //	   
   // LineItem lineItem = new LineItem();
   // lineItem.setItemId("EST-8");
   // lineItem.setLineNumber(1);
   // lineItem.setQuantity(10);
   // Item item = new Item();
   // item.setItemId("EST-8");
   // item.setListPrice(18.5);
   // lineItem.setItem(item);
   // order.addLineItem(lineItem);
   //	   
   // return order;
   // }

   // private String serializeOrder(Order order)
   // {
   // XStream xstream = new XStream(new DomDriver());
   // String xml;
   //	   
   // xstream.alias("order", Order.class);
   // xml = xstream.toXML(order);
   //	  
   // return xml;
   // }
   //   
}