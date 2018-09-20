package org.jboss.soa.esb.dvdstore;

/**
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
public class OrderItem {

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
