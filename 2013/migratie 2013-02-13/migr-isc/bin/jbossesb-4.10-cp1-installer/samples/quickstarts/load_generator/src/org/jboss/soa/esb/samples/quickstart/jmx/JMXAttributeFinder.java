/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.soa.esb.samples.quickstart.jmx;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

/**
 * Facade for querying JMX.     If user and password have a value other than null,
 * try to authenticate to secure HTTP before querying JMX.     
 *
 * @author <a href="tcunning@redhat.com">Tom Cunningham</a>
 */
public class JMXAttributeFinder {

	LoginContext loginContext = null;
	String user;
	String password;

	public JMXAttributeFinder() {
		user = null;
		password = null;
	}

	public JMXAttributeFinder(String f_user, String f_password) {
		user = f_user;
		password = f_password;
	}

	public String getResourceURL(String resource) throws MalformedURLException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL resURL = loader.getResource(resource);
		return resURL != null ? resURL.toString() : null;
	}

	public void login() {
		if ((user != null) && (password != null)) {
			try {
				CallbackHandler handler = new LGCallbackHandler(user,password);
				String authConf = getResourceURL("auth.conf");
				System.setProperty("java.security.auth.login.config", authConf);			
				LoginContext loginContext = new LoginContext("jboss_jaas",  handler);
				loginContext.login();
			} catch (Exception e) {
			}
		}
	}

	public Object query(ObjectName oname, String attname) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException {
		Object att = null;
		login();
		Properties env = new Properties();
		String url = new String("http://localhost:8080/invoker/JNDIFactory");
		env.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.HttpNamingContextFactory");
		env.setProperty(Context.PROVIDER_URL,url);
		env.setProperty("java.naming.factory.url.pkgs","org.jboss.naming.client");
		Context ctx;
		MBeanServerConnection server = null;
		try {
			ctx = new InitialContext(env);
			server = (MBeanServerConnection) ctx.lookup("jmx/invoker/RMIAdaptor");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
	
		att = server.getAttribute(oname, attname);
	
		try {
			logout();
		} catch (LoginException e) {
			e.printStackTrace();
		}
		return att;
	}

	public void logout() throws LoginException {
		if (loginContext != null) {
			loginContext.logout();
		}
	}

	public static void main (String args[]) {
		try {
			JMXAttributeFinder t = new JMXAttributeFinder();
			Object val = t.query(new ObjectName("jboss.esb:gateway-name=JMS-Gateway"), "LifeCycleState");	
			System.out.println(val);
			t.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

