/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.soa.esb.samples.quickstart.webservicewsa;


import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;

/**
 * WS-Addressing stateful service endpoint interface
 *
 * @author Thomas.Diesler@jboss.org
 * @author <a href="mailto:mageshbk@jboss.com">mageshbk@jboss.com</a>
 *
 * @since 24-Nov-2005
 */
@WebService(name = "WsaEndpoint", targetNamespace = "http://webservice.webservicewsa.quickstart.samples.esb.soa.jboss.org/", serviceName = "WsaService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface StatefulEndpoint extends Remote
{
   @WebMethod
   @Action(input="http://webservice.webservicewsa.quickstart.samples.esb.soa.jboss.org/addItem")
   public void addItem(String item) throws RemoteException;

   @WebMethod
   @Action(input="http://webservice.webservicewsa.quickstart.samples.esb.soa.jboss.org/checkout")
   public void checkout() throws RemoteException;

   @WebMethod
   @Action(input="http://webservice.webservicewsa.quickstart.samples.esb.soa.jboss.org/getItems")
   public String getItems() throws RemoteException;
}
