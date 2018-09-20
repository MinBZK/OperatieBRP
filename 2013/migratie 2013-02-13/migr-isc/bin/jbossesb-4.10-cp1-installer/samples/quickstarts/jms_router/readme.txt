Overview:
=========
  The purpose of the jms_router quickstart is to show how the JMSRouter 
  action can be configured.
  This quickstart also shows how a JMSCorrelationID can be used with the ESB.
  
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
  1. src/org/jboss/soa/esb/samples/quickstart/jmsrouter/test/SendJMSMessage
  	 Notice how the JMS Message is set with a correlationID.
  	 Notice how the receive from the response destination uses the
	 correlation id.

  2. jboss-esb.xml
	 Take a look at how the JMSRouter can be configured.

