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
package org.jboss.soa.esb.samples.quickstarts.bpm_orchestration2.esb_actions;


import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.apache.log4j.Logger;

public class MessageSpy extends AbstractActionLifecycle {
	
	protected ConfigTree	_config;
	private Logger logger = Logger.getLogger(MessageSpy.class);
	
	  // modify to use the logger
	public Message process(Message message) throws Exception {		
          Object payload = message.getBody().get();

          if (payload instanceof byte[]) {
              logger.info("Body: " + new String((byte[])payload));
          } else if (payload instanceof String) {
              logger.info("Body: " + payload);
          } else {
              logger.info("Body Contents are NULL");
          }
          return message;
	}
	 	  
    public MessageSpy(ConfigTree config) { _config = config; } 
  
}	