Overview:
=========
    This example demonstrates how to consume a 181 Web Service in an ESB action.
    This ESB will make a webservice request that requires a Order and ArrayList of LineItems.
    The webservice will return a OrderStatus. The ESB simply dislays the response on the 
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
	11:11:28,145 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
    11:11:28,177 INFO  [STDOUT] Order is: Order ID= 1
    Total Price=30.0
    Ship to=1234 my way
    Line Items:
             ID: 1
             Name: aname
             Price: 10.0
             ID: 2
             Name: aname2
             Price: 20.0
        09:45:28,678 WARN  [HttpMethodBase] Going to buffer response body of large or unknown size. Using
        getResponseBodyAsStream instead is recommended.
        09:45:28,680 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        09:45:28,680 INFO  [STDOUT] Response Map is: {processOrderResponse.return.comment=order processed,
        processOrderResponse.return.id=1, processOrderResponse.return.returnCode=1}
        09:45:28,681 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

Project file descriptions:
==========================

	jboss-esb.xml: 			
	There is no JMS gateway in this quickstart. A java client talks directly to the ESB
	listener queue called "queue/quickstart_webservice_consumer2_esb". The actions in 
	jboss-esb.xml convert the ESB message into to a webservice parameter Map, make a call 
	to the OrderProcessorWS webservice, then print the response on the console. The
	jbossesb-properties.xml is used when the service first boots up for self-registration
	based upon the category and name found in the jboss-esb.xml file.
        There are 2 different request action methods, "option1" and "option2" that correspond to the 
        Option 1 and Option 2 sections of this wiki: http://wiki.jboss.org/wiki/Wiki.jsp?page=SOAPClient.

	To submit a request using Option 1 use this action markup: 
        <action name="request-mapper" 
		class="org.jboss.soa.esb.samples.quickstart.webservice_consumer2.MyRequestAction"
		process="option1">
	</action>
	
	To submit a request using Option 2 use this action markup: 
        <action name="request-mapper" 
		class="org.jboss.soa.esb.samples.quickstart.webservice_consumer2.MyRequestAction"
		process="option2">
	</action>

	jndi.properties:
	Needed primarily for org.jboss.soa.esb.samples.quickstart.webservice_consumer2.test.SendESBMessage that is fired by ant runtest.

	log4j.xml:
	Needed to configure log4J used by both the quickstart and the ESB itself. A listener needs a place to log.

	src/../SendEsbMessage.java:
	Send a message directly the the ESB internal JMS listener. Demonstrates how one can bypass the 
	gateway and speak directly to an ESB service.
	
	src/../MyRequestAction.java
	Convert the message body into a webservice request parameter Map that only has one paramater called
	"orderProcessor.order" for the web service call. The request parameter map is named 
	"request-params".
	
	org.jboss.soa.esb.actions.soap.SOAPClient
	This class is used to make the call to the webservice. It will take the
	parameters that MyRequestAction set in the default location, call the webservice, then place
	the response in the default location. 
	
	src/../MyResponseAction.java
	Retrieve the webservice response and display it on the console.	The response location name
	is "order-status-response". 	
	
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
