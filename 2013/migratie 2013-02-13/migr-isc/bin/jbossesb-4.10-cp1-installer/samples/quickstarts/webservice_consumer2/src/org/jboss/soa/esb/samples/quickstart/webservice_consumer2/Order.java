package org.jboss.soa.esb.samples.quickstart.webservice_consumer2;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Order is the serialized object that this example is based around. It contains
 * a product name, the quantity of that product ordered, and the price per unit
 * of the product. It is serializable so that it can be bundled up in a message,
 * and the JSPs
 * 
 * @author <a href="mailto:tcunning@redhat.com">tcunning@redhat.com</a>
 * @since Version 4.2
 */
public class Order implements Serializable {
	private static final long serialVersionUID = -4620754343715487457L;
	private Long id;
	private ArrayList<LineItem> lineItems;
	private String shipTo;

	public ArrayList<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(ArrayList<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public float getTotalPrice() {
		float totalPrice = 0;
		{
			if (lineItems != null) {
				for (LineItem item : lineItems) {
					if (item.getPrice() != null)
						totalPrice += item.getPrice();
				}
			}
		}
		return totalPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("Order ID= " + this.id + "\nTotal Price="
				+ getTotalPrice() + "\nShip to=" + this.shipTo
				+ "\nLine Items:\n");
		if (lineItems != null) {
			for (LineItem lineItem : lineItems) {
				stringBuffer.append("\t ID: " + lineItem.getId() + "\n");
				stringBuffer.append("\t Name: " + lineItem.getName() + "\n");
				stringBuffer.append("\t Price: " + lineItem.getPrice() + "\n");
			}
		}
		else
		{
			stringBuffer.append("\t There are no Line Items!\n");
		}

		return stringBuffer.toString();
	}

}
