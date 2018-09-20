Overview:
=========
  This is another simple example of how to manually define and apply a Message
  Transformation within JBoss ESB.  

  This Quickstart is an extension of the "transformation_XML2XML_simple"
  Quickstart, demonstrating how  JBoss ESB Transformations can simplify your
  XSLT transformations by combining the power of XSLT with Java.  In this
  Quickstart, we use Java to perform the ugly string manipulation on the
  SampleOrder date field (see OrderDate.java) and use XSLT for what it's good at
  i.e. Templating.  Again, the transformed SampleOrder.xml message is just
  printed to the Java console (message before and after).

  A comprehensive description of message transformation can be found in
  the "Message Transformation" chapter in the ServicesGuide, located in the docs folder.


Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.

What to look at in this Quickstart:
===================================
  As stated above, reading the "Message Transformation" chapter in the ServicesGuide.pdf would
  be of great benefit in understanding JBoss ESB Transformations.

  jbossesb.xml:
    The ESB configuration file in this Quickstart simply defines a JMS Listener
    for receiving the contents of the SampleOrder.xml file located in this
    folder (line 31).  The listener configuration then executes the
    "SmooksTransformer" action for the Message Exchange between "A" and "B".

  smooks-res.xml:
    This file defines the Transformations for the Quickstart.  This time, there
    are a number of transformation configurations, all "targeted" at the same
    message.
          
  OrderDate.java:  (In the src tree).
    This is just a simple POJO class.  It gets populated by JBoss ESB
    Transformation (via Smooks) and is used by the XSLT (defined in
    smooks-res.xml) to generate the new OrderDate element in the output message.
    This class uses the standard Java SimpleDateFormat class for decoding the
    orderDate value and extracting the components of the date from it (day,
    month, zone etc).  This means you avoid having to do this in your XSLT code,
    which would be very ugly and difficult to maintain.
