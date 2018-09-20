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

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.status.Status;
import org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.status.HeartbeatStatus;
import org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.status.InventoryStatus;

/**
 * Hub.
 * 
 * @author dward at jboss.org
 */
public class Hub
{
	
	private static final Logger logger = Logger.getLogger(Hub.class);
	
	private static final Hub INSTANCE = new Hub();
	
	public static final Hub getInstance()
	{
		return INSTANCE;
	}
	
	// pretend database: spokeLocation -> alive
	private final Map<String,Boolean> heartbeat_DB = new HashMap<String,Boolean>();
	
	// pretend database: spokeLocation -> ( partName -> count )
	private final Map<String,Map<String,Integer>> inventory_DB = new HashMap<String,Map<String,Integer>>();
	
	private Hub() {}
	
	public Event processStatus(Status status)
	{
		assert status != null;
		Event event;
		if (status instanceof HeartbeatStatus)
		{
			HeartbeatStatus hs = (HeartbeatStatus)status;
			String spokeLocation = hs.getSpokeLocation();
			info("received Heartbeat for " + spokeLocation);
			setSpokeAlive(spokeLocation, true);
			event = new Event(Event.Type.HEARTBEAT, Event.Light.GREEN, spokeLocation);
		}
		else if (status instanceof InventoryStatus)
		{
			InventoryStatus is = (InventoryStatus)status;
			String spokeLocation = is.getSpokeLocation();
			String partName = is.getPartName();
			String identifier = spokeLocation + ":" + partName;
			int adjustment = is.getAdjustment();
			synchronized (inventory_DB)
			{
				int oldCount = getSpokePartCount(spokeLocation, partName);
				int newCount = oldCount + adjustment;
				if (newCount > -1)
				{
					info("will update inventory for " + identifier + " (oldCount=" + oldCount + ", newCount=" + newCount + ")");
					setSpokePartCount(spokeLocation, partName, newCount);
					event = new Event(Event.Type.INVENTORY, Event.Light.GREEN, spokeLocation);
				}
				else
				{
					warn("will NOT update inventory for " + identifier + " (oldCount=" + oldCount + ", newCount=" + newCount + ")");
					event = new Event(Event.Type.INVENTORY, Event.Light.RED, spokeLocation);
				}
			}
		}
		else
		{
			throw new IllegalArgumentException("unrecognized status: " + status);
		}
		return event;
	}
	
	public boolean isSpokeAlive(String spokeLocation)
	{
		assert spokeLocation != null;
		synchronized (heartbeat_DB)
		{
			Boolean alive = heartbeat_DB.get(spokeLocation);
			return (alive != null && alive.booleanValue());
		}
	}
	
	public void setSpokeAlive(String spokeLocation, boolean alive)
	{
		assert spokeLocation != null;
		synchronized (heartbeat_DB)
		{
			heartbeat_DB.put(spokeLocation, Boolean.valueOf(alive));
		}
	}
	
	public int getSpokePartCount(String spokeLocation, String partName)
	{
		assert spokeLocation != null;
		assert partName != null;
		synchronized (inventory_DB)
		{
			Map<String,Integer> location_to_part = inventory_DB.get(spokeLocation);
			if (location_to_part != null)
			{
				Integer count = location_to_part.get(partName);
				if (count != null)
				{
					return count.intValue();
				}
			}
			return 0;
		}
	}
	
	private void setSpokePartCount(String spokeLocation, String partName, int count)
	{
		assert spokeLocation != null;
		assert partName != null;
		synchronized (inventory_DB)
		{
			Map<String,Integer> location_to_part = inventory_DB.get(spokeLocation);
			if (location_to_part == null)
			{
				location_to_part = new HashMap<String,Integer>();
				inventory_DB.put(spokeLocation, location_to_part);
			}
			location_to_part.put(partName, Integer.valueOf(count));
		}
	}
	
	public void info(Object msg)
	{
		logger.info(msg);
	}
	
	public void warn(Object msg)
	{
		logger.warn(msg);
	}

}
