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
package org.jboss.soa.esb.samples.quickstart.businessrulesstateful;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.samples.quickstart.businessrulesstateful.dvdstore.Customer;
import org.jboss.soa.esb.samples.quickstart.businessrulesstateful.dvdstore.OrderHeader;

import java.util.Map;

public class SetupMessage extends AbstractActionPipelineProcessor {
	private Logger logger = Logger.getLogger(SetupMessage.class);
	private int status = 0;
	
	public Message process(Message message) throws ActionProcessingException {
		Map transformedBeans = (Map) message.getBody().get();

		if(transformedBeans != null) {
			OrderHeader header = (OrderHeader) transformedBeans.get("orderHeader");
			Customer customer = (Customer) transformedBeans.get("customer");	
			message.getBody().add("TheOrderHeader", header);
			message.getBody().add("TheCustomer", customer);		
			
			status = header.getStatusCode();
			
			if (status == 0 ) {
				message.getProperties().setProperty("dispose", false);
				message.getProperties().setProperty("continue", false);

			}
			
			if (status == 1 ) {			
				message.getProperties().setProperty("dispose", false);
				message.getProperties().setProperty("continue", true);
			}
		
			if (status == 2 ) {
				message.getProperties().setProperty("dispose", true);
				message.getProperties().setProperty("continue", true);
			}
					
			logger.info("Moved the transformed Order Header and Customer");
		}

		return message;
	}
	
	public SetupMessage(ConfigTree configTree) {
		// status = Integer.parseInt(configTree.getAttribute("status"));
	}
	
}
