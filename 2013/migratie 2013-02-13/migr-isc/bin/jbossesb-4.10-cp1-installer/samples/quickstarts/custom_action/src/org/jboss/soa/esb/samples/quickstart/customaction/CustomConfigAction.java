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
package org.jboss.soa.esb.samples.quickstart.customaction;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.actions.ActionProcessingException;
import java.util.Set;

public class CustomConfigAction extends AbstractActionLifecycle {
	protected ConfigTree	_config;
	public CustomConfigAction(ConfigTree config) { _config = config; } 
	
	public Message displayConfig(Message msg) throws ActionProcessingException {
		// Note: in and out message is being ignored
		
	    Set<String> names = _config.getAttributeNames();
	    System.out.println("****************************");
	    for (String attrName : names) {
	    	String value = _config.getAttribute(attrName);
	    	System.out.println("Attribute: " + attrName + " Value: " + value);
	    }
	    System.out.println("****************************");
	    
	    ConfigTree[] subElements = _config.getAllChildren();
	    // Note: even a sub-element can have attributes but trying to keep this simple
	    System.out.println("############################");
	    for (ConfigTree child : subElements) {
	    	System.out.println("SubElement: " + child.getName() + "Body: " + child.getWholeText());
	    }
	    System.out.println("############################");
		return msg;
	}
}
