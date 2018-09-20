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
 * (C) 2005-2009
 */
package org.jboss.soa.esb.samples.quickstart.webservice_proxy_versioning;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.log4j.Logger;

/**
 * Simple rpc-style webservice that returns invoice processing success.
 * 
 * @author dward at jboss.org
 */
@WebService(name = "Invoicing", targetNamespace = "http://webservice_proxy_versioning/invoicing")
public class InvoicingWS
{
	
	private static Logger logger = Logger.getLogger(InvoicingWS.class);
	
	@WebMethod
	@WebResult(name="success")
	public Boolean processInvoice(
			@WebParam(name="invoiceNumber") String invoiceNumber,
			@WebParam(name="processDate") Date processDate )
	{
		if ( logger.isInfoEnabled() )
		{
			logger.info("processInvoice called with invoiceNumber [" + invoiceNumber + "] and processDate [" + processDate + "]");
		}
		return (processDate != null ? Boolean.TRUE : Boolean.FALSE);
	}
	
}
