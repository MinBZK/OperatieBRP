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
package org.jboss.soa.esb.samples.quickstart.jmstransacted.test;

import static org.jboss.soa.esb.notification.jms.JMSPropertiesSetter.JMS_REDELIVERED;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.ActionLifecycle;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

/**
 * ThrowExceptionAction is a simple action that throws an exception until
 * the maximum rollback count has been 
 * if the message has not been redelivered (first call to this action). 
 * </p>
 * Note that this class in only intended to be used with the
 * jms_transacted quickstart.
 * </p>
 * @author <a href="mailto:daniel.bevenius@gmail.com">Daniel Bevenius</a>				
 *
 */
public class ThrowExceptionAction implements ActionLifecycle
{
	private Logger log = Logger .getLogger( ThrowExceptionAction.class );
	
	/**
	 * 	Number of times we should rollback. This is just used so that we 
	 * 	can specify when  the action should proceed without rolling back the transaction	
	 */
	private int maxRollbacks;
	
	/**
	 * 	Simple counter 
	 */
	private static int rollbackCounter;
	
	public ThrowExceptionAction( final ConfigTree config ) 
	{ 
		maxRollbacks = Integer.parseInt( config.getAttribute( "rollbacks", "3") );
	}
	
	/**
	 * Will rollback the the current transaction until the rollbackCounter
	 * is less than the configured amount.
	 * 
	 * @param message		- ESB Message object
	 * @return Message		- unchanged ESB Message Object
	 */
	public Message process( final Message message )
	{
		log.debug( "rollbackCounter [" + rollbackCounter + "], nr-of-rollbacks [" + maxRollbacks + "]");
		rollbackCounter++;
		if ( rollbackCounter <= maxRollbacks )
		{
			throw new IllegalStateException( "[Throwing Exception to trigger a transaction rollback]");
		}
		else
		{
				rollbackCounter = 0;
		}
		
		return message;
	}
	
	public void processException(final Message message, final Throwable th)   {  }
	public void destroy() throws ActionLifecycleException {}
	public void initialise() throws ActionLifecycleException {}
	
}
