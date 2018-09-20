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
package org.jboss.soa.esb.samples.quickstart.ruleagent.businessrules;

import java.util.HashMap;
import org.jboss.soa.esb.actions.AbstractActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.services.rules.Driver;
import org.jboss.soa.esb.services.rules.Policy;

public class ReviewMessage extends AbstractActionPipelineProcessor {
	private String input = "Sent Notification to:";
	
	public Message process(Message message) throws ActionProcessingException {
		
		Driver driver = (Driver) message.getBody().get("Driver");
		Policy policy = (Policy) message.getBody().get("Policy");	
		System.out.println("{ ================ " + input);		
		System.out.println("Name: " + driver.getName());
		System.out.println("Policy Price: " + policy.getBasePrice());
		System.out.println("} ================ " + input);
		
	
		return message;
	}
	
	public ReviewMessage(ConfigTree configTree) {
		input = configTree.getAttribute("stuff");
	}
	
}
