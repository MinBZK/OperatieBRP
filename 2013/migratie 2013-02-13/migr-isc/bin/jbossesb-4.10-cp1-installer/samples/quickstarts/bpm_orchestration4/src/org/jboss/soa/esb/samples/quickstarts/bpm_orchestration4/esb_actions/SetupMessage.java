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
package org.jboss.soa.esb.samples.quickstarts.bpm_orchestration4.esb_actions;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.store.Customer;
import org.jboss.soa.esb.store.OrderHeader;

import java.util.Map;

public class SetupMessage extends AbstractActionPipelineProcessor {
	private Logger logger = Logger.getLogger(SetupMessage.class);
	private int status = 0;

	public Message process(Message message) throws ActionProcessingException {

        Map javaResultMap = (Map) message.getBody().get();
        OrderHeader order = (OrderHeader) javaResultMap.get("orderHeader");
		Customer customer = (Customer) javaResultMap.get("customer");
		//List orderItems = (List) javaResultMap.get("orderItemList");

		customer.setStatus(status);

		logger.info("--------------------------------");
		logger.info("Customer: " + customer);
		logger.info("Order: " + order);
		logger.info("businessKey: " + order.getOrderId());
		logger.info("--------------------------------");

        message.getBody().add("orderHeader", order);
        message.getBody().add("customer", customer);

		message.getBody().add("order_orderId", order.getOrderId());
		message.getBody().add("order_orderPriority", order.getOrderPriority());
		message.getBody().add("order_totalAmount", order.getTotalAmount());
		message.getBody().add("order_discount", order.getOrderDiscount());
		message.getBody().add("businessKey", order.getOrderId());

		message.getBody().add("customer_firstName", customer.getFirstName());
		message.getBody().add("customer_lastName", customer.getLastName());
		message.getBody().add("customer_status", customer.getStatus());

		logger.info("Moved the transformed Order Header and Customer");

		return message;
	}

	public SetupMessage(ConfigTree configTree) { // demo purposes, passed in
													// from the jboss-esb.xml
		status = Integer.parseInt(configTree.getAttribute("status"));
	}

}