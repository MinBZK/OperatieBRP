Overview:
=========
  This quickstart demonstrates how to transform a comma separated value (CSV) file to an xml.
  The tranformation is done by configuring Smooks and performing two transformation, one
  transformation from CSV to an intermediate xml format, and a second transformation from 
  the intermediate xml format to the target xml.

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

  jbossesb.xml:
    The ESB configuration file in this Quickstart simply defines a JMS Listener
    for receiving the contents of the SampleOrder.csv file located in this
    folder. 

  smooks-res.xml:
    This file defines the Transformations for the Quickstart.  In this case it
    defines two transformations.
    
    1. The first part of the transformation uses smooks CSVParser to parse the CVS file and
       create an intermediate xml format that looks like this:
       
       <cvs-set>
          <cvs-record>
          <orderId>1</orderId>
          <orderDate>Wed Nov 15 13:45:28 EST 2006</orderDate>
          <statusCode>0</statusCode>
          ...
         </cvs-record>
       </cvs-set>

    2. The second part of the tranformation transforms the intermediate smooks format to the 
       target xml format. This is the second section in smooks-res.xml. It is
       here you can map the values to you're target xml format.
