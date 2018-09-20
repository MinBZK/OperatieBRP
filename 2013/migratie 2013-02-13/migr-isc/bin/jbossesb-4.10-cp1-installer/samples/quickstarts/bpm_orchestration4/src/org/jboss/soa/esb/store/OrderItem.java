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
 * (C) 2005-2006,
 * @author JBoss Inc.
 */

package org.jboss.soa.esb.store;

import java.io.Serializable;
/**
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
public class OrderItem implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    // <OrderLine position="1" quantity="1">
	// 		<Product productId="364" title="The 40-Year-Old Virgin " price="29.98"/>
	// </OrderLine>
	private int position;
	private int quantity;
	private String productId;
	private String title;
	private double price;
	
	/**
	 * @return Returns the position.
	 */
	public int getPosition() {
		return position;
	}
	/**
	 * @param position The position to set.
	 */
	public void setPosition(int position) {
		//System.out.println("**** position: " + position);
		this.position = position;
	}
	/**
	 * @return Returns the price.
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price The price to set.
	 */
	public void setPrice(double price) {
		//System.out.println("**** price: " + price);
		this.price = price;
	}
	/**
	 * @return Returns the productId.
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId The productId to set.
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return Returns the quantity.
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity The quantity to set.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return position + "," + quantity + "," + productId + "," + title + "," + price;
		
	}
}
