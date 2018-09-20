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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.MessageContext.Scope;

import org.jboss.logging.Logger;
import org.jboss.wsf.common.DOMUtils;
import org.jboss.internal.soa.esb.webservice.addressing.MAP;
import org.jboss.internal.soa.esb.webservice.addressing.MAPBuilder;
import org.jboss.internal.soa.esb.webservice.addressing.MAPBuilderFactory;
import org.jboss.internal.soa.esb.webservice.addressing.MAPEndpoint;
import org.jboss.wsf.common.handler.GenericSOAPHandler;
import org.w3c.dom.Element;

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

      MAPBuilder builder = MAPBuilderFactory.getInstance().getBuilderInstance();
      MAP addrProps = builder.inboundMap(msgContext);

      if (addrProps == null)
         throw new IllegalStateException("Cannot obtain AddressingProperties");

      String clientid = null;
      MAPEndpoint replyTo = addrProps.getReplyTo();
      List<Object> refParams = replyTo == null? new ArrayList<Object>() : replyTo.getReferenceParameters();
      for (Object obj : refParams)
      {
         if (obj instanceof Element)
         {
            Element el = (Element)obj;
            QName qname = DOMUtils.getElementQName(el);
            if (qname.equals(IDQN))
            {
               clientid = DOMUtils.getTextContent(el);
            }
         }
         else if (obj instanceof JAXBElement)
         {
            JAXBElement<String> el = (JAXBElement)obj;
            QName qname = el.getName();
            if (qname.equals(IDQN))
            {
               clientid = el.getValue();
            }
         }
         else
         {
            log.warn("Unsupported reference parameter found: " + obj);
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
      
      MAPBuilder builder = MAPBuilderFactory.getInstance().getBuilderInstance();
      MAP inProps = builder.inboundMap(msgContext);
      MAP outProps = builder.newMap();
      if (inProps.getReplyTo() != null)
         outProps.initializeAsDestination(inProps.getReplyTo());
      outProps.setAction("http://org.jboss.ws/addressing/stateful/actionReply");
      
      outProps.installOutboundMapOnServerSide(msgContext, outProps);
      msgContext.setScope(builder.newConstants().getServerAddressingPropertiesOutbound(), Scope.APPLICATION);

      return true;
   }
}
