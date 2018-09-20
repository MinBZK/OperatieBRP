/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.soa.esb.samples.quickstart.webservice_consumer2;

import java.io.Serializable;

/**
 * OrderStatus is the serialized object that this example is based around.   It
 * contains a product name, the quantity of that product ordered, and the
 * price per unit of the product.    It is serializable so that it can
 * be bundled up in a message, and the JSPs 
 * 
 * @author <a href="mailto:tcunning@redhat.com">tcunning@redhat.com</a>
 * @since Version 4.2
 */
public class OrderStatus implements Serializable {
	
	private static final long serialVersionUID = 0L;
	private Long id;	
	private String comment;
	private int returnCode;
	public static final int SUCCESS = 1;
	public static final int FAIL = 1;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	
}


