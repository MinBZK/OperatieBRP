package org.jboss.soa.esb.services.rules;

import java.io.Serializable;

/**
 * This represents a policy that a driver is applying for. 
 * 
 * Obviously in the real world, there are actuaries to mess things up, but lets just pretend there is
 * some simple base price and discount that we can calculate with relatively simple rules !
 * 
 * @author Michael Neale
 */
public class Policy implements Serializable
{
    private static final long serialVersionUID = 1L;
	private String type = "COMPREHENSIVE";
	private boolean approved = false;
	private int discountPercent = 0;
	private int basePrice;
	private Driver driver;

	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public int getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void applyDiscount(int discount) {
		discountPercent += discount;
	}
	public int getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(int basePrice) {
		this.basePrice = basePrice;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	
}
