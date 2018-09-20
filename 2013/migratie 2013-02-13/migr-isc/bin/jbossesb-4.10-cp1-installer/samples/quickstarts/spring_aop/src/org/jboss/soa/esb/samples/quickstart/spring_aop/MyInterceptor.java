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

import java.lang.reflect.Method;
import org.springframework.aop.AfterReturningAdvice;

/**
 * SpringAOP interceptor example.
 * 
 * <p>Simple after-method returning advice that intercepts a method
 * invocation. 
 * 
 * @see spring-context.xml 
 * 
 * @author <a href="mailto:james.williams@redhat.com">James Williams</a>.
 * 
 */
public class MyInterceptor implements AfterReturningAdvice
{

   public void afterReturning(Object returnValue, Method m, Object[] args,
         Object target) throws Exception
   {
      SaySomething something = (SaySomething) target;

      System.out.println("*****GREETING VALUE PRE-INTERCEPTOR*****");
      System.out.println(something.getGreeting());

      something
            .setGreeting("I used AOP to change the greeting! JMS has got nothin on me!");
      System.out.println("*****GREETING VALUE POST-INTERCEPTOR*****");

      System.out.println(something.getGreeting());
   }

}
