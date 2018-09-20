Overview:
=========
The purpose of the simple_transformation quickstart sample is to illustrate the
use of Smooks performing a simple transformation by converting a XML file into
Java POJOs.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

  A comprehensive description of message transformation can be found in 
  the "Message Transformation" chapter in the ServicesGuide, located in the docs folder.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant receive'.
  3. Open another command terminal window in this folder ("Window3"), type
     'ant runtest'.
  4. Switch back to Application Server console to see the output from the ESB
  5. When finished, interrupt the receiver ("Window2") using Ctrl-C.
  6. Undeploy the esb archive ("Window1"), type 'ant undeploy'.

What to look at in this Quickstart:
===================================
  As stated above, reading the "Message Transformation" chapter in the ServicesGuide.pdf would
  be of great benefit in understanding JBoss ESB Transformations.
  
  * jboss-esb.xml - uses the SmooksTransformerAction which is shipped with the
    JBoss ESB.
  * smooks-res.xml - handles the mappings from XML to the POJO/Bean
  * SampleOrder.xml - is the inbound test order. Feel free to modify the data
    (not the structure) and send it into the transformer multiple times (ant
    runtest) to see the results.
  * src\org\jboss\soa\esb\dvdstore\DVDStoreAction.java - converts the beans into
    a concatenated string.
  * src\org\jboss\soa\esb\dvdstore\OrderHeader.java - holds the header portion
    of the Order.
  * src\org\jboss\soa\esb\dvdstore\OrderItem.java - holds a line item for the
    Order.
  * src\org\jboss\soa\esb\dvdstore\Customer.java - holds the Customer associated
    with the Order.
