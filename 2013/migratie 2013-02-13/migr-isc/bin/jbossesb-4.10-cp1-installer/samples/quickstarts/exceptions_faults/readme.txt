Overview:
=========
  The purpose of the exceptions_faults quickstart example is to demonstrate
  how to handle exceptions in your action chains and service invocations. 


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

Things to Consider:
===================
  This quickstart illustrates the use of JMS message selectors:
                <jms-bus busid="ExceptionCaughtServiceGW">
                  <jms-message-filter
                      dest-type="QUEUE"
                      dest-name="queue/quickstart_exceptions_faults_GW"
                      selector="serviceName='ExceptionCaughtService'"
                  />

              </jms-bus>
              <jms-bus busid="ExceptionCaughtServiceESB">
                  <jms-message-filter
                      dest-type="QUEUE"
                      dest-name="queue/quickstart_exceptions_faults_ESB"
                      selector="serviceName='ExceptionCaughtService'"
                  />
              </jms-bus>

              Note: the matching selector for the gateway + native listener pairing.

  You should carefully review the jboss-esb.xml. It has two services:
  LostMessageService and ExceptionCaughtService.  
  The LostMessageService is aptly named because the user did not attempt to 
  "catch" exceptions in the action chain therefore they are unaware that an
  error occurred.  
  Also review the build.xml, it has runtest, runtest2, callAsync, callAsync2,
  callSync and callSync2 targets. The 2's are for the ExceptionCaughtService.
  The goal is to illustrate the differences in behavior depending on if you 
  are making an asynchronous (gateways are always async) or
  synchronous invocation (ServiceInvoker allows both options).

  1. ant runtest/callAsync: sends in a JMS message via the JMS gateway which in turn hands
  it off to a JMS listener (esb aware/native listener).  The execution of the action chain
  causes an exception:
      <action name="Bad Action"  class="org.jboss.soa.esb.samples.quickstart.exceptions.MyBasicAction"
          process="causesException" >
      </action>

   public Message causesException(Message message) throws ActionProcessingException {
   	  System.out.println("About to cause an exception");
   		throw new ActionProcessingException("BAD STUFF HAPPENED");
   }
   You should note that JustAnotherAction is never reached:
	   <action name="Final Good Action" 
	     class="org.jboss.soa.esb.samples.quickstart.exceptions.JustAnotherAction"  
	     process="displayMessage" >				  
	   </action> 
  
  2. Your custom buiness logic exceptions will be wrapped by an ActionProcessingException but still 
  be available on the client-side when using the 
  ServiceInvoker.deliverSync(String myMessage,int timeout) option. 
  SendEsbMessageSync.java demostrates ths when you run "ant callSync".

  3. The ExceptionCaughtService service demonstrates how the action chain is "unwound" when an
  exception is thrown downstream.  In this case, JustAnotherAction & MyExceptionHandlingAction will
  both have their exceptionMethod's triggered by the "Bad Action". 

  4. ant callSync2: demonstrates that an exception within the action chain, that is handled
  by the service is still propagated back to the client invoker.
