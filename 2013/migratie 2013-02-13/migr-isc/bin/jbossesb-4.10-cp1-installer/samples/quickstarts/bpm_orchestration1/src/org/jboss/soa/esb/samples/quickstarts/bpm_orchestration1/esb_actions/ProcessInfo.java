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
package org.jboss.soa.esb.samples.quickstarts.bpm_orchestration1.esb_actions;

import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.services.jbpm.Constants;
import org.apache.log4j.Logger;

public class ProcessInfo extends AbstractActionLifecycle {

	protected ConfigTree	_config;
	private Logger logger = Logger.getLogger(ProcessInfo.class);

	public Message process(Message message) {
	  logger.info("Token ID: " + message.getBody().get("jbpmTokenId"));
      logger.info("Process ID: " + message.getBody().get(Constants.PROCESS_INSTANCE_ID));
      logger.info("Process Name: " + message.getBody().get("jbpmProcessDefName"));
	  logger.info("Process Version: " + message.getBody().get("jbpmProcessDefVersion"));
	  return message;
	}
	
	public ProcessInfo(ConfigTree config) {
		_config = config;
	}
}
