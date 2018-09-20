Overview:
=========
	This example demonstrates how to consume a 181 Web Service in an ESB action.
    This ESB will make a webservice request that requires a single "toWhom" string parameter.
    The webservice will return a greeting response. The ESB simply dislays the response on the 
    console.  

Running this quickstart:
========================
	Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts and a 
	more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
    1.  In a command terminal window in this folder ("Window1"), type 'ant undeploy-jms-dests'.
    1.  In a command terminal window in this folder ("Window1"), type 'ant deploy'.
    2.  Open another command terminal window in this folder ("Window2"), type 'ant runtest'.
    3.  Switch back to Application Server console to see the output from the ESB

	'runtest' target description:
	In a separate command prompt window, run "ant runtest" to shoot a JMS message
	into the listener which will then invoke the MyJMSListenerAction and display
	it to the console.  You can modify the build.xml to change the phrase 
	"Hello World" to something else and re-run "ant runtest".
	
	You should output that looks like this, after running "ant runtest":
	&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    12:35:47,975 INFO  [STDOUT] Request map is: {sayHello.toWhom=Jimbo}
    12:35:47,975 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    12:35:47,990 WARN  [HttpMethodBase] Going to buffer response body of large or unknown size. Using 
                       getResponseBodyAsStream instead is recommended.
    12:35:47,991 INFO  [STDOUT] 
    &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    12:35:47,991 INFO  [STDOUT] Response Map is: 
              <env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'>
                 <env:Header></env:Header>
              <env:Body>
              <hel:sayHelloResponse xmlns:hel='http://webservice_consumer1/helloworld'>
                 <return>Hello World Greeting for 'Jimbo' on Sat Jul 14 12:35:47 EDT 2007</return>
              </hel:sayHelloResponse></env:Body></env:Envelope>
    12:35:47,991 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

Project file descriptions:
==========================

	jboss-esb.xml: 			
	There is no JMS gateway in this quickstart. A java client talks directly to the ESB
	listener queue called "queue/quickstart_webservice_consumer1_esb". The actions in 
	jboss-esb.xml convert the ESB message into to a webservice parameter Map, make a call 
	to the HelloWorldWS webservice, then print the response on the console. The 
	jbossesb-properties.xml is used when the service first boots up for self-registration 
	based upon the category and name found in the jboss-esb.xml file.

	jndi.properties:
	Needed primarily for org.jboss.soa.esb.samples.quickstart.webservice_consumer1.test.SendESBMessage that is fired by ant runtest.

	log4j.xml:
	Needed to configure log4J used by both the quickstart and the ESB itself. A listener needs a place to log.

	src/../SendEsbMessage.java:
	Send a message directly the the ESB internal JMS listener. Demonstrates how one can bypass the 
	gateway and speak directly to an ESB service.
	
	src/../MyRequestAction.java
	Convert the message body into a webservice request parameter Map that only has one paramater called
	"sayHello.toWhom" for the web service call. The request parameter map is named 
	"helloworld-request-parameters".
	
	org.jboss.soa.esb.actions.soap.SOAPClient
	This class is used to make the call to the webservice. It will take the
	parameters that MyRequestAction set in "paramsLocation", call the webservice, then place
	the response in "responseLocation". This is zero Java code ESB wiring in jboss-esb.xml.
	
	src/../MyResponseAction.java
	Retrieve the webservice response and display it on the console.	The response location name
	is "helloworld-response". 	
	
	build.xml:
	Targets and structure description:
	*	the classpath property pulls the jbossesb-properties.xml file and the juddi configuration to the
		front of the list
	*	the echoCP task is useful for making sure what you think is in your classpath is actually in your classpath
		Usage is: ant echoCP > myclasspath.txt 
		This generates a file called myclasspath.txt which can be reviewed in a text editor
	*	the run task calls the Launcher passing in 3 arguments the most important are the esb-config.xml and 
		esb-config-gateway.xml files
	*	the runtest task calls the org.jboss.soa.esb.samples.quickstart.helloworld.test.SendEsbMessage class and passes in an argument representing
		the string-based message to be pused into the queue the gateway is listening on. 
