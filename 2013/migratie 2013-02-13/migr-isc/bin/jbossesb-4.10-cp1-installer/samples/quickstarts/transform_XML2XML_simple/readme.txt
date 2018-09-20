Overview:
=========
  This is a very basic example of how to manually define and apply a Message
  Transformation within JBoss ESB.  It applies a very simple XSLT to a
  SampleOrder.xml message and prints the before and after XML to the console.
  
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
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.

What to look at in this Quickstart:
===================================
  As stated above, reading the "Message Transformation" chapter in the ServicesGuide.pdf would
  be of great benefit in understanding JBoss ESB Transformations.

  jboss-esb.xml:
    The ESB configuration file in this Quickstart simply defines a JMS Listener
    for receiving the contents of the SampleOrder.xml file located in this
    folder.  This XML message will be transformed by this service.  

  smooks-res.xml:
    This file defines the Transformations for the Quickstart.  In this case, it
    simply defines a single XSL transformation for the order line items.
