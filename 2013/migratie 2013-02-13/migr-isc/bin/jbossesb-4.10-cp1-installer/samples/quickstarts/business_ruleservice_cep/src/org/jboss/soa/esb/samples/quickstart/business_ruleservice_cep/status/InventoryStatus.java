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
package org.jboss.soa.esb.samples.quickstart.business_ruleservice_cep.status;

/**
 * InventoryStatus.
 * 
 * @author dward at jboss.org
 */
@SuppressWarnings("serial")
public class InventoryStatus extends Status
{
	
	private String spokeLocation;
	private String partName;
	private int adjustment;

	public InventoryStatus(String spokeLocation, String partName, int adjustment)
	{
		super();
		assert spokeLocation != null;
		assert partName != null;
		this.spokeLocation = spokeLocation;
		this.partName = partName;
		this.adjustment = adjustment;
	}
	
	public String getSpokeLocation()
	{
		return spokeLocation;
	}
	
	public String getPartName()
	{
		return partName;
	}
	
	public int getAdjustment()
	{
		return adjustment;
	}
	
	@Override
	public String toString()
	{
		return new StringBuilder()
			.append(super.toString())
			.append(", spokeLocation=")
			.append(getSpokeLocation())
			.append(", partName=")
			.append(getPartName())
			.append(", adjustment=")
			.append(getAdjustment())
			.toString();
	}

}
