Overview:
=========
  The purpose of the recipient list quickstart sample is demonstrate the
  recipient list EIP pattern (as defined here:
  http://www.enterpriseintegrationpatterns.com/RecipientList.html) using a
  static router as implemented in org.jboss.soa.esb.actions.StaticRouter and a
  content based router (CBR) as implemented in
  org.jboss.soa.esb.actions.ContentBasedRouter

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. In a command terminal window in this folder ("Window2"), type
     'ant receive-destination'.
  3. In a command terminal window in this folder ("Window3"), type
     'ant receive-destination1'.
  4. In a command terminal window in this folder ("Window4"), type
     'ant receive-destination2'.
  5. Open another command terminal window in this folder ("Window5"), type
     'ant runtest-cbr'.
  6. Switch back to Application Server and receiver consoles to see the output
     from the ESB
  7. When finished, interrupt the receivers using Ctrl-C and, in folder
     "Window1", type 'ant undeploy'.

Things to Consider:
===================
  You can modify the "totalAmount" element value in SampleOrder.xml to control
  the path that is followed thru the CBR. The valid ranges of values are less
  than 100 or greater than 99.99 - the behavior modeled is suggested  in the
  EIP pattern definition, that being a credit account monitor where purchases
  below a certain cost are reviewed by a single credit agency while purchases
  above a certain cost are reviewed by that same credit agency and two
  additional credit agencies.
  
  The quickstart makes use of the queues illustrated (the queues are shown 
  in boxes) in the following diagram. You might review the build.xml file to 
  see how it is setup. More details on the build.xml can be found later in 
  this document.

      application initiated
               |
               |
               V
  -----------------------------              --------------------------------- 
  |qsrecipientlist_cbr_Request| -----------> |qsrecipientlist_regular_Request|---> output below 100.00
  -----------------------------              --------------------------------- 
               |                           /
               |                          / 
               V                         |
---------------------------------------  |   --------------------------- 
|qsrecipientlist_static_router_Request| ---> |qsrecipientlist_1_Request|---> output above 99.99
---------------------------------------  |   ---------------------------
                                         |
                                          \
                                           \ --------------------------- 
                                             |qsrecipientlist_2_Request|---> output above 99.99
                                             --------------------------- 


  * build.xml - This quickstart implements the following additional targets:
    * runtest - send message thru gateway listener in MyJMSListener directly to
      destination queue
    * static-router - send message thru static router gateway in
      org.jboss.soa.esb.actions.StaticRouter
    * runtest-cbr - send message thru CBR router gateway in
      org.jboss.soa.esb.actions.ContentBasedRouter
