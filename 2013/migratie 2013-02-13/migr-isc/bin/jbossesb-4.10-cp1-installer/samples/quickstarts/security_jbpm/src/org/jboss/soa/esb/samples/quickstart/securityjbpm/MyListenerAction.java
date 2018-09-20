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
package org.jboss.soa.esb.samples.quickstart.securityjbpm;

import javax.xml.namespace.QName;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingDetailFaultException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import java.io.FilePermission;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.PermissionCollection;
import java.security.Policy;
import javax.security.auth.Subject;

public final class MyListenerAction extends AbstractActionLifecycle
{
    private ConfigTree config;
	private String serviceName;
    
    public MyListenerAction(final ConfigTree config)
    {
        this.config = config;
		serviceName = config.getParent().getAttribute("service-name");
    }
    
    public Message displayMessage(final Message message) throws Exception
    {
		System.out.println("'ServiceName '" + serviceName + "'. Subject:" + Subject.getSubject(AccessController.getContext()));
        return message;
    }
}
