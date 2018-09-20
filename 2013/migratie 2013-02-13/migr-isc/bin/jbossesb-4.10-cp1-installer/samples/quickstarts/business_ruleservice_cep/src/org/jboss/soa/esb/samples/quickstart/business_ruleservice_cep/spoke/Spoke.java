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
package org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.spoke;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.status.HeartbeatStatus;
import org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.status.StatusSender;

/**
 * Spoke.
 * 
 * @author dward at jboss.org
 */
public class Spoke
{
	
	private final String location;
	
	private final Map<String,Part> parts = new HashMap<String,Part>();
	
	public Spoke(String location)
	{
		assert location != null;
		this.location = location;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public Part addPart(String partName)
	{
		assert partName != null;
		synchronized (parts)
		{
			if (parts.containsKey(partName))
			{
				throw new IllegalStateException("Part with name: " + partName + " already exists.");
			}
			Part part = new Part(this, partName);
			parts.put(partName, part);
			return part;
		}
	}
	
	public Part getPart(String partName)
	{
		assert partName != null;
		synchronized (parts)
		{
			return parts.get(partName);
		}
	}
	
	public Collection<Part> getParts()
	{
		synchronized (parts)
		{
			return parts.values();
		}
	}
	
	public void heartbeat() throws Exception
	{
		StatusSender.getInstance().sendStatus(new HeartbeatStatus(getLocation()));
	}

}
