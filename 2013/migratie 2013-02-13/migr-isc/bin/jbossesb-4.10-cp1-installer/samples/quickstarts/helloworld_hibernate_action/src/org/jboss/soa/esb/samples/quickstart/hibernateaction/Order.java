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
package org.jboss.soa.esb.samples.quickstart.hibernateaction;

import java.io.Serializable;

/**
 * Order is the serialized object that this example is based around.   It
 * contains a product name, the quantity of that product ordered, and the
 * price per unit of the product.    It is serializable so that it can
 * be bundled up in a message, and the JSPs 
 * 
 * @author <a href="mailto:tcunning@redhat.com">tcunning@redhat.com</a>
 * @since Version 4.2
 */
public class Order implements Serializable {
	private static final long serialVersionUID = -4620754343715487457L;
	private Long id;	
	private String product;
	private Integer quantity;
	private Float price; 

	/**
	 * Constructor that takes no values. 
	 */
	public Order() { }
	
	/**
	 * Cosntructor - sets the product, quantity, price, and sets the id
	 * to a default value.
	 * @param f_product product name
	 * @param f_quantity quantity of products
	 * @param f_price price of one product item
	 */
	public Order(String f_product, Integer f_quantity, Float f_price) {
		id = new Long(0);
		this.product = f_product;
		this.quantity = f_quantity;
		this.price = f_price;	
	}
	
	/**
	 * Id getter.
	 * @return id
	 */
	public Long getId() {
		return id;
	}
		
	/**
	 * Id mutator
	 * @param f_id id
	 */
	public void setId(Long f_id) {
		this.id = f_id;
	}

	/**
	 * Product getter.
	 * @return product name
	 */
	public String getProduct() {
		return product;
	}	
	
	/**
	 * Product mutator.
	 * @param f_product product name
	 */
	public void setProduct(String f_product) {
		product = f_product;
	}

	/**
	 * Product quantity getter.
	 * @return product quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/** 
	 * Product quantity mutator.
	 * @param f_quantity product quantity
	 */
	public void setQuantity(Integer f_quantity) {
		this.quantity = f_quantity;
	}		

	/**
	 * Product price getter.
	 * @return product price
	 */
	public Float getPrice() {
		return price;
	}
	
	/**
	 * Product price mutator
	 * @param f_price product price
	 */
	public void setPrice(Float f_price) {
		this.price = f_price;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "id [" + id + "]"
		        + "product [" + product + "]  " 
			+ "quantity [" + quantity + "]  " 
			+ "price [" + price + "] ";
	}
}

