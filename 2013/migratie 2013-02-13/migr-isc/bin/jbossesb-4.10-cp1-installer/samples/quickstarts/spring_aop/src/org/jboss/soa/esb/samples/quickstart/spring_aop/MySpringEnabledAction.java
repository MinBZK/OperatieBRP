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
package org.jboss.soa.esb.samples.quickstart.spring_aop;

import org.jboss.soa.esb.actions.AbstractSpringAction;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

/**
 * Spring enabled action that leverages SpringAOP.
 * 
 * 
 * @see org.jboss.soa.esb.actions.AbstractSpringAction 
 * @see spring-context.xml
 * 
 * @author <a href="mailto:james.williams@redhat.com">James Williams</a>.
 * 
 */
public class MySpringEnabledAction extends AbstractSpringAction
{
   public MySpringEnabledAction(ConfigTree config) throws Exception
   {
      super(config);
   }

   public Message sayHelloAopStyle(Message message) throws Exception
   {

      SaySomething hello = (SaySomething) getBeanFactory().getBean(
            "helloObject");
      // interceptor will get applied here
      // check the console output to see the interceptor changed the message
	  String helloString = (String) message.getBody().get();
      hello.setGreeting(helloString);
	  message.getBody().add(hello.getGreeting());

      return message;
   }

}