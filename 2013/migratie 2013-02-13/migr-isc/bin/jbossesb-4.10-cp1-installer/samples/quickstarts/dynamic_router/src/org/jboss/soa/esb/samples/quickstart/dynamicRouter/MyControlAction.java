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
package  org.jboss.soa.esb.samples.quickstart.dynamicRouter;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import org.jboss.soa.esb.samples.quickstart.dynamicRouter.test.*;
import java.util.Hashtable;

import java.util.Enumeration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.QueueSender;
import javax.naming.NamingException;

public class MyControlAction extends AbstractActionLifecycle {
	protected ConfigTree _config;

	public MyControlAction(ConfigTree config) {
		_config = config;
	}

	public Message processControlMessage(Message message) throws Exception {

		Hashtable theHashtable = QsHashtable.readHash();
		String tm = message.getBody().get().toString();

		try {
			// The message contains the queue name and status separated by ":"
			// for example - queue/queueName:OK - any queue associated with
			// a value of "OK" is added to the dynamic router data store
			String[] theStrings = tm.split(":");

			// Make the data store dynamic - to reflect changes in queue status
			if (theHashtable.containsKey(theStrings[0])) {
				theHashtable.remove(theStrings[0]);
			}

			theHashtable.put(theStrings[0], theStrings[1]);

		} catch (Throwable t) {
			t.printStackTrace();
		}
		QsHashtable.writeHash(theHashtable);
		return message;

	} /* method */

} /* class */
