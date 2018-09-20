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
package org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.spoke.Part;
import org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.spoke.Spoke;
import org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.status.StatusSender;

/**
 * Test.
 * 
 * @author dward at jboss.org
 */
public class Test {
	
	private static final Map<String,Spoke> spokes = new HashMap<String,Spoke>();
	static {
		for (String location : new String[]{"Atlanta", "Boulder", "Chicago"}) {
			Spoke spoke = new Spoke(location);
			spokes.put(location, spoke);
			for (String partName : new String[]{"widget", "sprocket", "nut", "bolt"}) {
				spoke.addPart(partName);
			}
		}
	}
	
	private static final Random RANDOM = new Random();
	
	public Test() {}
	
	private static synchronized int random(int max)
	{
		return RANDOM.nextInt(max);
	}
	
	private static boolean adjustPart(String[] args, boolean increment) throws Exception
	{
		boolean understood = false;
		for (int a=1; a < args.length; a++)
		{
			String[] sp = args[a].split(":");
			for (Spoke spoke : spokes.values())
			{
				if (spoke.getLocation().equalsIgnoreCase(sp[0]))
				{
					for (Part part : spoke.getParts())
					{
						if (part.getName().equalsIgnoreCase(sp[1]))
						{
							understood = true;
							if (increment) {
								part.increment();
							} else {
								part.decrement();
							}
						}
					}
				}
			}
		}
		return understood;
	}
	
	public static void main(String... args) throws Exception
	{
		boolean understood = false;
		if (args.length > 0)
		{
			StatusSender sender = StatusSender.getInstance();
			sender.startConnection();
			if ("startSession".equals(args[0]))
			{
				understood = true;
				sender.startSession();
			}
			else if ("sendHeartbeat".equals(args[0]))
			{
				
				for (int a=1; a < args.length; a++)
				{
					for (Spoke spoke : spokes.values())
					{
						if (spoke.getLocation().equalsIgnoreCase(args[a]))
						{
							understood = true;
							spoke.heartbeat();
						}
					}
				}
			}
			else if ("populateInventory".equals(args[0]))
			{
				understood = true;
				for (Spoke spoke : spokes.values())
				{
					for (Part part : spoke.getParts())
					{
						part.adjust(random(10));
					}
				}
			}
			else if ("incrementPart".equals(args[0]))
			{
				understood = adjustPart(args, true);
			}
			else if ("decrementPart".equals(args[0]))
			{
				understood = adjustPart(args, false);
			}
			else if ("stopSession".equals(args[0]))
			{
				understood = true;
				sender.stopSession();
			}
			sender.stopConnection();
		}
		if (!understood)
		{
			throw new Exception("could not understand command [" + args + "]");
		}
	}

}
