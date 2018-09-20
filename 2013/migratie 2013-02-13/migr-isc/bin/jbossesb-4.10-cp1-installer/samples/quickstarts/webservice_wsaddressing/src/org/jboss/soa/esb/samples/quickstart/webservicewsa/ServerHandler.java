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

import java.net.URISyntaxException;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.ws.addressing.AddressingBuilder;
import javax.xml.ws.addressing.AddressingProperties;
import javax.xml.ws.addressing.EndpointReference;
import javax.xml.ws.addressing.JAXWSAConstants;
import javax.xml.ws.addressing.ReferenceParameters;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.MessageContext.Scope;

import org.jboss.logging.Logger;
import org.jboss.util.xml.DOMUtils;
import org.jboss.ws.core.jaxws.handler.GenericSOAPHandler;

/**
 * A server side handler for the ws-addressing
 *
 * @author Thomas.Diesler@jboss.org
 * @since 24-Nov-2005
 */
public class ServerHandler extends GenericSOAPHandler
{
   // Provide logging
   private static Logger log = Logger.getLogger(ServerHandler.class);

   private static final QName IDQN = StatefulEndpointImpl.IDQN;

   @Override
   public boolean handleInbound(MessageContext msgContext)
   {
      log.info("handleRequest");

      AddressingProperties addrProps = (AddressingProperties)msgContext.get(JAXWSAConstants.SERVER_ADDRESSING_PROPERTIES_INBOUND);
      if (addrProps == null)
         throw new IllegalStateException("Cannot obtain AddressingProperties");

      String clientid = null;
      EndpointReference replyTo = addrProps.getReplyTo();
      ReferenceParameters refParams = replyTo!=null ? replyTo.getReferenceParameters() : null;
      if (refParams != null)
      {
         for (Object obj : refParams.getElements())
         {
            SOAPElement el = (SOAPElement)obj;
            QName qname = DOMUtils.getElementQName(el);
            if (qname.equals(IDQN))
            {
               clientid = DOMUtils.getTextContent(el);
            }
         }
      }

      if (clientid == null)
         throw new IllegalStateException("Cannot obtain client id");

      // put the clientid in the message context
      msgContext.put("clientid", clientid);
      msgContext.setScope("clientid", Scope.APPLICATION);
      return true;
   }

   @Override
   public boolean handleOutbound(MessageContext msgContext)
   {
      log.info("handleResponse");

      try
      {
         AddressingProperties inProps = (AddressingProperties)msgContext.get(JAXWSAConstants.SERVER_ADDRESSING_PROPERTIES_INBOUND);
         AddressingBuilder builder = AddressingBuilder.getAddressingBuilder();

         builder.newAddressingConstants();
         AddressingProperties outProps = builder.newAddressingProperties();
         outProps.initializeAsReply(inProps, false);
         outProps.setAction(builder.newURI("http://org.jboss.ws/addressing/stateful/actionReply"));

         msgContext.put(JAXWSAConstants.SERVER_ADDRESSING_PROPERTIES_OUTBOUND, outProps);
         msgContext.setScope(JAXWSAConstants.SERVER_ADDRESSING_PROPERTIES_OUTBOUND, Scope.APPLICATION);
      }
      catch (URISyntaxException ex)
      {
         throw new IllegalStateException("Cannot handle response", ex);
      }

      return true;
   }
}
