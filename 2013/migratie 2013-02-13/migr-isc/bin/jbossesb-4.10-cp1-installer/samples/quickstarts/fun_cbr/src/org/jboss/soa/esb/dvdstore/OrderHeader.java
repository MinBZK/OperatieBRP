package org.jboss.soa.esb.dvdstore;

import java.util.Calendar;

/**
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
public class OrderHeader {

	// <Order orderId="1" orderDate="Wed Nov 15 13:45:28 EST 2006" statusCode="0" netAmount="59.97" totalAmount="64.92" tax="4.95">
	private String orderId;
	private Calendar orderDate;
	private int statusCode;
	private double netAmount;
	private double totalAmount;
	private double tax;

	/**
	 * @return Returns the netAmount.
	 */
	public double getNetAmount() {
		return netAmount;
	}
	/**
	 * @param netAmount The netAmount to set.
	 */
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	/**
	 * @return Returns the orderDate.
	 */
	public Calendar getOrderDate() {
		return orderDate;
	}
	/**
	 * @param orderDate The orderDate to set.
	 */
	public void setOrderDate(Calendar orderDate) {
		this.orderDate = orderDate;
	}
	/**
	 * @return Returns the orderId.
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId The orderId to set.
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return Returns the statusCode.
	 */
	public int getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode The statusCode to set.
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return Returns the tax.
	 */
	public double getTax() {
		return tax;
	}
	/**
	 * @param tax The tax to set.
	 */
	public void setTax(double tax) {
		this.tax = tax;
	}
	/**
	 * @return Returns the totalAmount.
	 */
	public double getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount The totalAmount to set.
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return orderId + ", " + (orderDate == null?null:orderDate.getTime()) + ", " + statusCode + ", " + netAmount + ", " + totalAmount + ", " + tax + ", ";
	}
}
