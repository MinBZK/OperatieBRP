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

/**
 * Event.
 * 
 * @author dward at jboss.org
 */
public class Event
{
	
	public static enum Type {
		HEARTBEAT,
		INVENTORY
	}
	
	public static enum Light {
		GREEN,
		RED
	}
	
	private Type type;
	private Light light;
	private String spokeLocation;
	
	public Event(Type type, Light light, String spokeLocation)
	{
		this.type = type;
		this.light = light;
		this.spokeLocation = spokeLocation;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public Light getLight()
	{
		return light;
	}
	
	public String getSpokeLocation()
	{
		return spokeLocation;
	}

	@Override
	public String toString()
	{
		return new StringBuilder()
			.append(getClass().getSimpleName())
			.append(": type=")
			.append(getType())
			.append(", light=")
			.append(getLight())
			.append(", spokeLocation=")
			.append(getSpokeLocation())
			.toString();
	}

}
