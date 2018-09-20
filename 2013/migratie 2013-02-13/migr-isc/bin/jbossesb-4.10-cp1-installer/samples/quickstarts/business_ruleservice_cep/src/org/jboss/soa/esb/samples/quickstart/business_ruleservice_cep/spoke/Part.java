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

import org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.status.StatusSender;
import org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.status.InventoryStatus;

/**
 * Part.
 * 
 * @author dward at jboss.org
 */
public class Part
{
	
	private final Spoke spoke;
	private final String name;
	
	public Part(Spoke spoke, String name)
	{
		assert spoke != null;
		assert name != null;
		this.spoke = spoke;
		this.name = name;
	}
	
	public Spoke getSpoke()
	{
		return spoke;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void increment() throws Exception
	{
		adjust(1);
	}
	
	public void decrement() throws Exception
	{
		adjust(-1);
	}
	
	public void adjust(int adjustment) throws Exception
	{
		StatusSender.getInstance().sendStatus(new InventoryStatus(getSpoke().getLocation(), getName(), adjustment));
	}

}
