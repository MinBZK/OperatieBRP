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

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Status.
 * 
 * @author dward at jboss.org
 */
@SuppressWarnings("serial")
public abstract class Status implements Serializable
{

	private static final AtomicLong sequence = new AtomicLong(0L);
	
	private long id;
	
	public Status()
	{
		synchronized (sequence)
		{
			id = sequence.incrementAndGet();
		}
	}
	
	public long getId()
	{
		return id;
	}
	
	@Override
	public String toString()
	{
		return new StringBuilder()
			.append(getClass().getSimpleName())
			.append(": id=")
			.append(getId())
			.toString();
	}

}
