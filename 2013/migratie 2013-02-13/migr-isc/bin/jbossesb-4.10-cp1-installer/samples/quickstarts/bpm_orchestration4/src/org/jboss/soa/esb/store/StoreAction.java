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

package org.jboss.soa.esb.store;

import java.util.List;

import org.jboss.soa.esb.actions.AbstractActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

/**
 * Store action.
 * <p/>
 * This action uses bean value objects that were prepopulated by Smooks in an earlier action on the pipline.
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
public class StoreAction extends AbstractActionPipelineProcessor {

	public StoreAction(ConfigTree configTree) { }
	
	public Message process(Message message) throws ActionProcessingException {
		
		StringBuffer results = new StringBuffer();		
	  // "order", "customer", "orderItem" is set in the smooks config file: from-dvdstore.xml
		OrderHeader header = (OrderHeader) message.getBody().get("orderHeader");
		Customer customer = (Customer) message.getBody().get("customer");
		List orderItems = (List) message.getBody().get("orderItemList");
		// System.out.println("************************\n\n");
		// System.out.println(header.toString());
		// System.out.println(customer.toString());
		// System.out.println(orderItems.toString());
		// System.out.println("************************\n\n");
		results.append("Demonstrates Smooks ability to rip the XML into Objects\n");
		results.append("********** StoreAction - Order Value Objects Populated ***********\n");
		results.append("Header: " + header + "\n");
		results.append("Customer: " + customer + "\n");
		if(orderItems != null) {
			results.append("Order Items (" + orderItems.size() + "):\n");
			for(int i = 0; i < orderItems.size(); i++) {
				results.append("\t" + i + ": " + orderItems.get(i) + "\n");
			}
		}
		results.append("\n****************************************************************** ");
		message.getBody().add("OrderHeader",header);
		message.getBody().add("Customer",customer);
		message.getBody().add("OrderItems",orderItems);
		// Take this "parsed" and reformatted output
		message.getBody().add(results.toString());
		return message;
	}
}
