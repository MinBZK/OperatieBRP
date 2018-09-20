package org.jboss.soa.esb.samples.quickstart.webservice_consumer2;

import java.io.Serializable;

public class LineItem implements Serializable {

   /**
    *
    */
   private static final long serialVersionUID = 0L;
   private Long id;
   private Float price;
   private String name;

   public Long getId()
   {
      return id;
   }
   public void setId(Long id)
   {
      this.id = id;
   }

   public Float getPrice()
   {
      return price;
   }
   public void setPrice(Float price)
   {
      this.price = price;
   }

   public String getName()
   {
      return name;
   }
   public void setName(String name)
   {
      this.name = name;
   }

   public String toString()
   {
      return "Line Item ID= " + this.id + "\nPrice=" + this.price + "\nShip To=" + this.name;
   }

}
