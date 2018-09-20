/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, JBoss Inc., and individual contributors as indicated
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
package org.jboss.soa.esb.samples.quickstart.hibernateaction;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * SessionFactorySingleton initializes a SessionFactory by looking it up
 * in the JNDI InitialContext.     In order for the Hibernate Interceptor
 * to work, it is very important that the same SessionFactory object is 
 * used by the ESB and the client code, so this example uses the SessionFactory
 * that the ESB has bound to JNDI.    
 * 
 * @author tcunning@redhat.com
 */
public class SessionFactorySingleton {
	private SessionFactorySingleton() {
	}

	private static final Configuration config = new Configuration();
	private static Logger logger = Logger.getLogger(SessionFactorySingleton.class);
	private static SessionFactory sf;

	private static String CONFIG_FILE = "hibernate.cfg.xml";
	private static final String HIBERNATE_JNDI = "SessionFactory";

	
	/**
	 * Return the SessionFactory, or initialize it if it is null.
	 * @return SessionFactory
	 */
	public static SessionFactory getInstance() {
		if (sf == null) {
			init();		
		}
		return sf;	
	}

	/**
	 * Initialize the sessionFactory by grabbing it out of the JNDI 
	 * InitialContext.     It is very important that the example uses the
	 * same SessionFactory object that is initialized by the ESB Hibernate
	 * Listener because hibernate events can only be intercepted on the 
	 * SessionFactory that the interceptor is set on.    By grabbing
	 * the SessionFactory out of JNDI, we guarantee that we do that
	 * in the case of a redeploy.
	 */
	private static synchronized void init() {
		try {
			String jndiName = config.getProperty(HIBERNATE_JNDI);
			if (jndiName == null) {
				jndiName = HIBERNATE_JNDI;
			}
			
			if (jndiName != null) {
				InitialContext ic = new InitialContext();
				sf = (SessionFactory) ic.lookup(jndiName);
			} else {
				sf = null;
			}	
		} catch (Exception e) {
			e.printStackTrace();	
		}
		
		if (sf == null) {
			logger.error("Could not find SessionFactory in JNDI - Interceptors will not work!");
			config.configure(CONFIG_FILE);
			sf = config.buildSessionFactory();
		}
	}

	/**
	 * Close the sessionFactory and set the class's sessionFactory to null.
	 */
	public static void close() {
		if (sf != null) {
			sf.close();	
		}
		sf = null;
	}
}
