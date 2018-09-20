Overview:
=========
    This example demonstrates how to use Wise SOAPClient action to consume a 181 Web Service in an ESB action.
    This ESB will make a webservice request that requires a single "toWhom" string parameter.
    The webservice will return a greeting response. The ESB simply displays the request and response on the
    console.  

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
	
	You should output that looks like this, after running "ant runtest":
        13:20:27,431 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        13:20:27,432 INFO  [STDOUT] Request map is: {toWhom=Jimbo}
	13:20:27,433 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	13:20:27,830 INFO  [STDOUT] parsing WSDL...
	13:20:28,962 INFO  [STDOUT] generating code...
	13:20:29,000 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise/generated/HelloWorld.java
	13:20:29,049 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise/generated/HelloWorldWSService.java
	13:20:29,053 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise/generated/ObjectFactory.java
	13:20:29,057 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise/generated/SayHello.java
	13:20:29,058 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise/generated/SayHelloResponse.java
	13:20:29,066 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise/generated/package-info.java
	13:20:30,010 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	13:20:30,010 INFO  [STDOUT] Response Map is: {result=Hello World Greeting for 'Jimbo' on Tue Sep 09 13:20:29 CST 2008}
        13:20:30,010 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

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

	log4j.xml:
	Needed to configure log4J used by both the quickstart and the ESB itself. A listener needs a place to log.

	src/../SendEsbMessage.java:
	Send a message directly the the ESB internal JMS listener. Demonstrates how one can bypass the 
	gateway and speak directly to an ESB service.

        src/../SendJMSMessage.java:
	Send a message to JMS gateway listener and speak to an ESB service.
	
	src/../MyRequestAction.java
	Convert the message body into a webservice request parameter Map that only has one paramater called
	"toWhom" for the web service call.
	
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
