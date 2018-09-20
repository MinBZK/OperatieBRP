Overview:
=========
  This example demonstrates how to develop and host a 181 Web Service on the
  JBoss Application Server. This Web Service is then used to make a synchronous
  call into the ESB via a "native client".  This quickstart has no gateway, it
  uses a native "ESB Aware" listener and the client side code demonstrates how
  to use the MessageDeliveryAdapter.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
=======
  1. In a command terminal window in the quickstart folder type 'ant deploy'.

  2. Using a browser, hit the following URL: "http://localhost:8080/jbossws".
  
  	Click on the "View a list of deployed services" link.

     You should see something like the following:
     -----------------------------------------------------------------------
     |   jboss.ws:context=Quickstart_native_client,endpoint=HelloWorldWS   |
     |   http://127.0.0.1:8080/Quickstart_native_client/HelloWorldWS?wsdl  |
     -----------------------------------------------------------------------

  3. Use your favorite Web Service testing tool to invoke the Web Service. A
     great one is called SoapUI at www.soapui.org.  

     The ESB console should produce the following messages:

15:37:56,149 INFO  [STDOUT] HelloWorld Hit! ?
15:37:56,193 INFO  [STDOUT] 
&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
15:37:56,193 INFO  [STDOUT] Body: ?
15:37:56,193 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
15:37:56,194 INFO  [STDOUT] 
&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
15:37:56,194 INFO  [STDOUT] !ERROR!
15:37:56,194 INFO  [STDOUT] Break!!!!
15:37:56,194 INFO  [STDOUT] For Message: 
15:37:56,194 INFO  [STDOUT] Hello From ESB MyAction: ?
15:37:56,194 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
15:37:56,194 INFO  [STDOUT] 
&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
15:37:56,194 INFO  [STDOUT] !ERROR!
15:37:56,194 INFO  [STDOUT] Break!!!!
15:37:56,194 INFO  [STDOUT] For Message: 
15:37:56,194 INFO  [STDOUT] Hello From ESB MyAction: ?
15:37:56,194 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
15:37:56,195 INFO  [STDOUT] org.jboss.soa.esb.couriers.FaultMessageException: org.jboss.soa.esb.actions.ActionProcessingException: Break!!!!
15:37:56,195 INFO  [STDOUT] org.jboss.soa.esb.couriers.FaultMessageException: org.jboss.soa.esb.actions.ActionProcessingException: Break!!!!

The !ERROR! is expected because of how MyAction.java's  playWithMessage(Message message):Message method.
