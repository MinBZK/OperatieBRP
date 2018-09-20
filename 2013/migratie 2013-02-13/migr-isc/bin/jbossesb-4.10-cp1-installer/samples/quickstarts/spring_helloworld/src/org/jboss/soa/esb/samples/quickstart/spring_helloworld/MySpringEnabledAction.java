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
package org.jboss.soa.esb.samples.quickstart.spring_helloworld;

import org.jboss.soa.esb.actions.AbstractSpringAction;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;

/**
 * Spring enabled action that makes a hello world call.
 * 
 * @see org.jboss.soa.esb.actions.AbstractSpringAction
 * @see spring-context-hello.xml
 * @see spring-context-goodbye.xml
 * 
 * @author <a href="mailto:james.williams@redhat.com">James Williams</a>.
 * 
 */
public class MySpringEnabledAction extends AbstractSpringAction
{

   public MySpringEnabledAction(ConfigTree config) throws Exception
   {
      configTree = config;
   }

   public Message displayMessage(Message message) throws Exception
   {
      logHeader();
      System.out.println("Body: " + message.getBody().get());
      logFooter();

      return message;
   }

   public Message sayHelloSpring(Message message) throws Exception
   {

      SaySomething hello = (SaySomething) getBeanFactory().getBean(
            "helloObject");

      Body msgBody = message.getBody();
      String contents = (String) message.getBody().get();
      StringBuffer sb = new StringBuffer();
      sb.append(contents);
      sb.append("\n");
      sb.append(hello.getGreeting());

      msgBody.add(sb.toString());

      return message;
   }

   public Message sayGoodbyeSpring(Message message) throws Exception
   {
      SaySomething goodbye = (SaySomething) getBeanFactory().getBean(
            "goodbyeObject");

      Body msgBody = message.getBody();
       String contents = (String) message.getBody().get();
      StringBuffer sb = new StringBuffer();
      sb.append(contents);
      sb.append("\n");
      sb.append(goodbye.getGreeting());

      msgBody.add(sb.toString());

      return message;
   }
}