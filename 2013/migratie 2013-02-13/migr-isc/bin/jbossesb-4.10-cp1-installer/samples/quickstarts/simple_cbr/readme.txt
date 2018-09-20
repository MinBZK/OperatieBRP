Overview:
=========
  The purpose of the simple_cbr quickstart is to demonstrate the Content Based
  Router in JBoss ESB.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. In a command terminal window in this folder ("Window2"), type
     'ant receiveExpress'.
  3. In a command terminal window in this folder ("Window3"), type
     'ant receiveNormal'.
  4. Open another command terminal window in this folder ("Window4"), type
     'ant runtest'.
  5. Switch back to Application Server console, "Window2" and "Window3" to see
     the output from the ESB
  6. When finished, interrupt the receivers using Ctrl-C and, in folder
     "Window1", type 'ant undeploy'.
  
Things to Consider:
===================
  - Use a text editor to change the "totalAmount" in the order above/below $50
    and re-execute ant runtest to see the order flash up in the different
    receivers.
  
    <Order orderId="1" orderDate="Wed Nov 15 13:45:28 EST 2006" statusCode="0" 
      netAmount="59.97" totalAmount="64.92" tax="4.95">

  - SimpleCBRRules-XPath.drl is a JBoss Rules ruleset that has simple logic that
    evaluates the totalAmount attribute of the SampleOrder.xml.  Over $50 means
    Free Express shipping, Under $50 means Normal shipping. 
