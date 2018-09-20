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
package org.jboss.test.ws.jaxws.samples.wssecurity;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.jboss.logging.Logger;
import org.jboss.ws.annotation.EndpointConfig;

@WebService(name = "Hello", serviceName = "HelloService", targetNamespace = "http://org.jboss.ws/samples/wssecurity")
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class HelloJavaBean
{
   private Logger log = Logger.getLogger(HelloJavaBean.class);

   @WebMethod
   public UserType echoUserType(@WebParam(name = "user") UserType in0)
   {
      log.info(in0);
      return in0;
   }
}
