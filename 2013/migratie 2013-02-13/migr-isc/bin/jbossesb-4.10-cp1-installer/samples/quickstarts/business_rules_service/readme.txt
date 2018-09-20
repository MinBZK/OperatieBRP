Overview:
=========
  Demonstrates the use of the BusinessRulesProcessor which allows for
  modification of the POJOs attached to an ESB Message.  The example uses Rules
  to calculate the priority associated with an inbound order for later routing,
  plus it calculates the discount percentage associated with an order.

  Make sure you have run simple_cbr, transformation_XML2POJO and fun_cbr
  quickstarts as their principles are used in this more complex example.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

  A comprehensive description of message transformation can be found in
  ServicesGuide.pdf, located in the docs/services folder.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.

What to look at in this Quickstart:
===================================
  Review the 3 different .drl files to see the distinction between business
  rules used for calculation/validation and rules used for routing. 
  
  The customer status is actually set in the jboss-esb.xml via the UpdateCusutomerStatus
  action since it is not provided with the inbound XML. You don't want a
  customer to determine their status.   In a real world situation, another
  system would be integrated via an action/service, that first calculates the
  customer's status (frequent flier, volume of previous purchases, etc.)
  Try setting the "status" to different values and see how the customer
  status is used in MyBusinessRules.drl.

