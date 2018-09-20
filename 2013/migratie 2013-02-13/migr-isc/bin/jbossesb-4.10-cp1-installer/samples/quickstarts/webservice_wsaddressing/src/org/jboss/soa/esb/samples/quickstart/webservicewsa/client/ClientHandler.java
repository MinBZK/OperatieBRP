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
package org.jboss.soa.esb.samples.quickstart.webservicewsa.client;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.ws.addressing.AddressingBuilder;
import javax.xml.ws.addressing.AddressingConstants;
import javax.xml.ws.addressing.AddressingProperties;
import javax.xml.ws.addressing.EndpointReference;
import javax.xml.ws.addressing.JAXWSAConstants;
import javax.xml.ws.addressing.ReferenceParameters;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.MessageContext.Scope;

import org.jboss.logging.Logger;
import org.jboss.util.xml.DOMUtils;
import org.jboss.ws.core.jaxws.handler.GenericSOAPHandler;
import org.jboss.ws.core.utils.UUIDGenerator;
import org.jboss.soa.esb.samples.quickstart.webservicewsa.StatefulEndpointImpl;

/**
 * A client side handler for the ws-addressing
 *
 * @author Thomas.Diesler@jboss.org
 * @since 24-Nov-2005
 */
public class ClientHandler extends GenericSOAPHandler
{
   // Provide logging
   private static Logger log = Logger.getLogger(ClientHandler.class);

   private static final QName IDQN = StatefulEndpointImpl.IDQN;

   private static int maxClientId;
   private String clientid;

   @Override
   public boolean handleOutbound(MessageContext msgContext)
   {
      try
      {
         AddressingBuilder builder = AddressingBuilder.getAddressingBuilder();
         AddressingConstants ADDR = builder.newAddressingConstants();

         AddressingProperties outProps = builder.newAddressingProperties();
         outProps.setTo(builder.newURI("uri:jaxrpc-samples-wsaddressing/TestService"));
         outProps.setAction(builder.newURI("http://org.jboss.ws/addressing/stateful/action"));

         EndpointReference replyTo = builder.newEndpointReference(new URI(ADDR.getAnonymousURI()));
         outProps.setReplyTo(replyTo);
         outProps.setMessageID(builder.newURI("urn:uuid:"+ UUIDGenerator.generateRandomUUIDString()));

         // Assign a new clientid
         if (clientid == null)
         {
            clientid = "clientid-" + (++maxClientId);
            log.info("New clientid: " + clientid);
         }

         ReferenceParameters refParams = replyTo.getReferenceParameters();
         refParams.addElement(getClientIdElement(clientid));

         msgContext.put(JAXWSAConstants.CLIENT_ADDRESSING_PROPERTIES_OUTBOUND, outProps);
         msgContext.setScope(JAXWSAConstants.CLIENT_ADDRESSING_PROPERTIES_OUTBOUND, Scope.APPLICATION);
      }
      catch (URISyntaxException ex)
      {
         throw new IllegalStateException("Cannot handle request", ex);
      }

      return true;
   }

   @Override
   public boolean handleInbound(MessageContext msgContext)
   {
      AddressingProperties addrProps = (AddressingProperties)msgContext.get(JAXWSAConstants.CLIENT_ADDRESSING_PROPERTIES_INBOUND);
      if (addrProps == null)
         throw new IllegalStateException("Cannot obtain AddressingProperties");

      ReferenceParameters refParams = addrProps.getReferenceParameters();
      for (Object obj : refParams.getElements())
      {
         SOAPHeaderElement el = (SOAPHeaderElement)obj;
         QName qname = DOMUtils.getElementQName(el);
         if (qname.equals(IDQN))
         {
            clientid = DOMUtils.getTextContent(el);
         }
      }

      if (clientid == null)
         throw new IllegalStateException("Cannot obtain clientid");

      return true;
   }

   private String getClientIdElement(String clientid)
   {
      String qualname = IDQN.getPrefix() + ":" + IDQN.getLocalPart();
      StringBuffer buffer = new StringBuffer("<" + qualname);
      buffer.append(" xmlns:" + IDQN.getPrefix() + "='" + IDQN.getNamespaceURI() + "'");
      buffer.append(">" + clientid + "</" + qualname + ">");
      return buffer.toString();
   }
}
