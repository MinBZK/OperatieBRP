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
 * (C) 2005-2009
 */
package org.jboss.soa.esb.samples.quickstart.webservice_proxy_routed;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Simple rpc-style webservice that returns a hello message (using the passed-in name) and the current date.
 * 
 * @author dward at jboss.org
 */
@WebService(name = "Hello", targetNamespace = "http://webservice_proxy_routed/hello")
public class HelloWS
{
	
	@WebMethod
	public String sayHello(@WebParam(name = "toWhom") String toWhom)
	{
		return "Hello '" + toWhom + "' on " + new java.util.Date();
	}
	
}
