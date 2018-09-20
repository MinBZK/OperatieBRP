Overview:
=========
    This is another example of how to use SmookHandler in Wise SOAPClient to do soap message transformation.   
    The tranformation is done by confguring 'smooks-handler-config' for
SOAPClient. This sample simply adds the soap header to the soap message. 

Running this quickstart:
========================
    Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts and a 
    more detailed descripton of the different ways to run the quickstarts.

    WISE creates, compiles and loads some webservice classes at runtime.  For this reason,
    you should increase the PermGen space if you are running this example through the
    JBoss App Server (Vs the JBoss ESB Server).  To do this, just add the following to
    the "JAVA_OPTS" variable in the Server "run" script:
        -XX:MaxPermSize=128m

To Run:
===========================
    1.  Open wise-core.properties and modify the propertie 'wise.tmpDir' there to suit your environment.   
    2.  In a command terminal window in this folder ("Window1"), type 'ant deploy'.
    3.  Open another command terminal window in this folder ("Window2"), type 'ant runtest'.
    4.  Switch back to Application Server console to see the output from the ESB

	'runtest' target description:
	In a separate command prompt window, run "ant runtest" to shoot a ESB message
	into the listener which will then invoke the MyRequestAction, SOAPClient and MyResponse
        and display it to the console.
	
	17:06:05,571 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	17:06:05,571 INFO  [STDOUT] Request map is: {toWhom=Jimbo}
	17:06:05,571 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	17:06:05,576 INFO  [STDOUT] parsing WSDL...
	17:06:05,603 INFO  [STDOUT] generating code...
	17:06:05,605 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise3/generated/HelloWorld.java
	17:06:05,836 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise3/generated/HelloWorldWSService.java
	17:06:05,839 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise3/generated/ObjectFactory.java
	17:06:05,844 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise3/generated/SayHello.java
	17:06:05,846 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise3/generated/SayHelloResponse.java
	17:06:05,849 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise3/generated/package-info.java
	17:06:06,225 INFO  [STDOUT] 
	Outbound message:
	17:06:06,230 INFO  [STDOUT] <env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header><sign:sign xmlns:sign='uri://		
	org.example.webservices.signature.Sign'/></env:Header><env:Body><ns1:sayHello xmlns:ns1='http://webservice_consumer_wise3/helloworld'><toWhom>Jimbo
	17:06:06,230 INFO  [STDOUT] </toWhom>
	17:06:06,230 INFO  [STDOUT] </ns1:sayHello>
	17:06:06,230 INFO  [STDOUT] </env:Body></env:Envelope>
	17:06:06,246 INFO  [STDOUT] 
	Inbound message:
	17:06:06,246 INFO  [STDOUT] <env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><ns2:sayHelloResponse 	
        xmlns:ns2='http://webservice_consumer_wise3/helloworld'><return>Hello World Greeting for &apos;Jimbo&apos; on Tue Sep 09 17:06:06 CST 2008
	17:06:06,246 INFO  [STDOUT] </return>
	17:06:06,246 INFO  [STDOUT] </ns2:sayHelloResponse>
	17:06:06,246 INFO  [STDOUT] </env:Body></env:Envelope>
	17:06:06,255 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	17:06:06,255 INFO  [STDOUT] Response Map is: {result=Hello World Greeting for 'Jimbo' on Tue Sep 09 17:06:06 CST 2008}
	17:06:06,255 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&



    5. When finished, undeploy the application by typing 'ant undeploy'.

Project file descriptions:
==========================

	jboss-esb.xml: 			
        The actions in jboss-esb.xml convert the ESB message into to a webservice parameter Map, make a call
        to the HelloWorldWS webservice, then print the response on the console. The jbossesb-properties.xml
        is used when the service first boots up for self-registration based upon the category and name found
        in the jboss-esb.xml file.

	jndi.properties:
	Needed primarily for org.jboss.soa.esb.samples.quickstart.webservice_consumer_wise.SendESBMessage that is fired by ant runtest.

        wise-core.properties
        Needed to configure SOAPClient based on Wise project.
        
        smooks-handler.xml
        Smooks style configuration xml. Needed to configure the SmooksHander. 
        
	log4j.xml:
	Needed to configure log4J used by both the quickstart and the ESB itself. A listener needs a place to log.

	src/../SendEsbMessage.java:
	Send a message directly the the ESB internal JMS listener. Demonstrates how one can bypass the 
	gateway and speak directly to an ESB service.

        src/../SendJMSMessage.java:
	Send a message to JMS gateway listener and speak to an ESB service.
	
	src/../MyRequestAction.java
	Convert the message body into a webservice request parameter Map.
	
	src/../SOAPClient
	This class is used to make the call to the webservice. It will take the
	parameters map that MyRequestAction set in message body, call the webservice, then place
	the response in message.
	
	src/../MyResponseAction.java
	Retrieve the webservice response and display it on the console.
	
	build.xml:
	Targets and structure description:
	*	the classpath property pulls the jbossesb-properties.xml file and the juddi configuration to the
		front of the list
	*	the echoCP task is useful for making sure what you think is in your classpath is actually in your classpath
		Usage is: ant echoCP > myclasspath.txt 
		This generates a file called myclasspath.txt which can be reviewed in a text editor
	*	the runtest task calls the org.jboss.soa.esb.samples.quickstart.webservice_consumer_wise.SendEsbMessage class and passes in an argument representing
		the string-based message to be pused into esb message aware listener queue. 
	*	the sendjms task calls the org.jboss.soa.esb.samples.quickstart.webservice_consumer_wise.SendJMSMessage class and passes in an argument representing
		the string-based message to be pused into the queue the gateway is listening on.  
