Overview:
=========
The purpose of the simple_transformation quickstart sample is to illustrate the
use of Smooks performing a simple transformation by converting a XML file into
Java POJOs, in this case it uses 2 different but similiar XML files to transform 
to a common set of POJOs.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

  A comprehensive description of message transformation can be found in
  the "Message Transformation" chapter in the ServicesGuide, located in the docs folder.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  3. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  4. Switch back to Application Server console to see the output from the ESB
  5. Undeploy the esb archive ("Window1"), type 'ant undeploy'.

What to look at in this Quickstart:
===================================
  As stated above, reading the "Message Transformation" chapter in the ServicesGuide.pdf would
  be of great benefit in understanding JBoss ESB Transformations.
  
  * jboss-esb.xml - uses the SmooksTransformerAction which is shipped with the
    JBoss ESB.
  * smooks-config.xml - imports two unique smooks resource files: from-dvdstore.xml 
    and from-petstore.xml from the transforms directory.
  * from-dvdstore.xml - default-target-profile="from:dvdstore" is the key element
    that determines when this set of mappings is applied.  
  * from-petstore.xml - default-target-profile="from:petstore" is the key element
    that determines when this set of mappings is applied.  This element is setup
    as a property on the esb Message via check-origin.groovy
  * check-origin.groovy - evaluates the inbound XML, looking for the either an 
    OrderLines or LineItems tag in the XML.  OrderLines means this is DVDStore
    related and LineItems means it is Petstore related.  
  * SampleOrder.xml - is the inbound test order. Feel free to modify the data
    (not the structure) and send it into the transformer multiple times (ant
    runtest) to see the results.
  * SamplePetsOrder.xml - is the inbound test order. Feel free to modify the data
    (not the structure) and send it into the transformer multiple times (ant
    runtest) to see the results.    
  * src\org\jboss\soa\esb\store\StoreAction.java - converts the beans into
    a concatenated string and places it back into the default location of the 
    message body.  This is useful for seeing the converted string come back as a
    reply.
  * src\org\jboss\soa\esb\store\OrderHeader.java - holds the header portion
    of the Order.
  * src\org\jboss\soa\esb\store\OrderItem.java - holds a line item for the
    Order.
  * src\org\jboss\soa\esb\store\Customer.java - holds the Customer associated
    with the Order.
  
