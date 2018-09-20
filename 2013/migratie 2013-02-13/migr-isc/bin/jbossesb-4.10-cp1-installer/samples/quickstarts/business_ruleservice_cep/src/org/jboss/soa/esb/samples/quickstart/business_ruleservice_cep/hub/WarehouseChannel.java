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
 * (C) 2005-2010
 */
package org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.hub;

import org.apache.log4j.Logger;
import org.drools.runtime.Channel;
import org.jboss.soa.esb.Configurable;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.helpers.ConfigTree;

/**
 * WarehouseChannel.
 * 
 * @author dward at jboss.org
 */
public class WarehouseChannel implements Channel, Configurable
{
	
	private static final Logger logger = Logger.getLogger(WarehouseChannel.class);
	
	private String warehouseName = null;
	
	public WarehouseChannel() {}
	
	public void setConfiguration(ConfigTree config) throws ConfigurationException
	{
		for (ConfigTree child : config.getChildren("property"))
		{
			if ("warehouse-name".equals(child.getAttribute("name")))
			{
				warehouseName = child.getRequiredAttribute("value");
				break;
			}
		}
		if (warehouseName == null)
		{
			throw new ConfigurationException("null warehouse-name");
		}
	}
	
	public String getWarehouseName()
	{
		return warehouseName;
	}

	public void send(Object object)
	{
		logger.info("will notify the " + getWarehouseName() + " warehouse of: " + object);
	}

}
