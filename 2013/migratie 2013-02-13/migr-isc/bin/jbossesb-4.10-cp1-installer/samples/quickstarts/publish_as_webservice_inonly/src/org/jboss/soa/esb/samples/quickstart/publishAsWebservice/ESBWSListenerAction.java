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
package org.jboss.soa.esb.samples.quickstart.publishAsWebservice;

import javax.xml.namespace.QName;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingDetailFaultException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class ESBWSListenerAction extends AbstractActionLifecycle
{
    protected ConfigTree _config;
    
    public ESBWSListenerAction(ConfigTree config)
    {
        _config = config;
    }
    
    public Message displayMessage(Message message) throws Exception
    {
        final String request = (String)message.getBody().get() ;
        if (request.contains("Error")) {
            final String detail = "<say:sayFault xmlns:say=\"http://www.jboss.org/sayHi\"><say:code>" +
                "myErrorCode" + "</say:code><say:faultString>" + "myDescription" +
                "</say:faultString></say:sayFault>" ;
            throw new ActionProcessingDetailFaultException(new QName("http://www.jboss.org/sayHi", "myErrorCode"), "myDescription", detail) ;
        }
        
        System.out.println("Received request: " + request) ;
        final String responseMsg = "<say:sayHiResponse xmlns:say=\"http://www.jboss.org/sayHi\"><say:arg0>" +
            "Response from ESB Service" + "</say:arg0></say:sayHiResponse>" ;
        message.getBody().add(responseMsg);
        return message;
    }
}
