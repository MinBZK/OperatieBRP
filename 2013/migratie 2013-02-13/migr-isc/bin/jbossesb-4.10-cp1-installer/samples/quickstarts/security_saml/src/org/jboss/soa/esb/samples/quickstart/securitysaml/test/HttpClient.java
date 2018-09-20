/*
 * JBoss, Home of Professional Open Source Copyright 2008, Red Hat Middleware
 * LLC, and individual contributors by the @authors tag. See the copyright.txt
 * in the distribution for a full listing of individual contributors.
 * 
 * This is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this software; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF
 * site: http://www.fsf.org.
 */
package org.jboss.soa.esb.samples.quickstart.securitysaml.test;

import org.jboss.remoting.Client;
import org.jboss.remoting.InvokerLocator;
import org.jboss.internal.soa.esb.util.StreamUtils;
import org.jboss.remoting.transport.http.HTTPMetadataConstants;

import java.util.HashMap;
import java.util.Map;

public class HttpClient
{
   // Default locator values
   private static String transport = "http";
   private static String host = "localhost";
   private static int port = 5400;
   private static String payload;

   public void makeInvocation(String locatorURI) throws Throwable
   {
      InvokerLocator locator = new InvokerLocator(locatorURI);
      System.out.println("Calling remoting server with locator uri of: " + locatorURI);

      Client remotingClient = new Client(locator);
      remotingClient.connect();

      Map metadata = new HashMap();
      metadata.put("TYPE", "POST");
      remotingClient.invokeOneway(payload, metadata );

      System.out.println("Sent http post to server.");
      Integer responseCode = (Integer) metadata.get(HTTPMetadataConstants.RESPONSE_CODE);
      String responseMessage = (String) metadata.get(HTTPMetadataConstants.RESPONSE_CODE_MESSAGE);
      System.out.println("Response code from server: " + responseCode);
      System.out.println("Response message from server: " + responseMessage);
      remotingClient.disconnect();

   }

   public static void main(String[] args) throws Exception
   {
      if(args != null && args.length == 4)
      {
         transport = args[0];
         host = args[1];
         port = Integer.parseInt(args[2]);
		 payload = StreamUtils.getResourceAsString(args[3], "UTF-8");
      }
      String locatorURI = transport + "://" + host + ":" + port;
      HttpClient client = new HttpClient();
      try
      {
         client.makeInvocation(locatorURI);
      }
      catch(Throwable e)
      {
         e.printStackTrace();
      }
   }
}

