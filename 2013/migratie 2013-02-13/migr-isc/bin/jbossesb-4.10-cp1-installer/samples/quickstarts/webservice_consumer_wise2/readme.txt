Overview:
=========
    This example demonstrates how to use Wise SOAPClient and SmooksMapper to consume a jaxws Web Service in an ESB action.
    In this quickstart, we use SmooksRequestMapper to transform the ExternalObject(see ExternalObject.java)
    to JAXWS annotated web service request object (see ComplexObject.java under the wise.tmpDir configured in wise-core.properties) 
    and call a Web Service. We also configure a SmooksResponseMapper to transform the web service result to an ExternalObject. 

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
        and display it to the console.  The output should be similar to the following
	
	12:34:28,046 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	12:34:28,051 INFO  [STDOUT] Request map is: {external=org.jboss.soa.esb.samples.quickstart.webservice_consumer_wise2.ExternalObject@1970f19
		[internal=org.jboss.soa.esb.samples.quickstart.webservice_consumer_wise2.InternalObject@604ed2[text=Jimbo,number=1],date=Wed Mar 07 04:27:00 GMT 2007]}
	12:34:28,052 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	12:34:28,072 INFO  [STDOUT] parsing WSDL...
	12:34:28,174 INFO  [STDOUT] generating code...
	12:34:28,178 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise2/generated/ComplexObject.java
	12:34:28,183 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise2/generated/ObjectFactory.java
	12:34:28,187 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise2/generated/PingComplexObject.java
	12:34:28,190 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise2/generated/PingComplexObjectResponse.java
	12:34:28,192 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise2/generated/PingWS.java
	12:34:28,194 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise2/generated/PingWSService.java
	12:34:28,196 INFO  [STDOUT] org/jboss/soa/esb/samples/quickstart/webservice_consumer_wise2/generated/package-info.java
	12:34:28,754 INFO  [STDOUT] Ping with ComplexObject:stirngField=Jimbo;integerField=1;calendarField=java.util.GregorianCalendar[time=?,
		areFieldsSet=false,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="GMT+00:00",offset=0,dstSavings=0,
		useDaylight=false,transitions=0,lastRule=null],firstDayOfWeek=2,minimalDaysInFirstWeek=4,ERA=1,YEAR=2007,MONTH=2,WEEK_OF_YEAR=1,
		WEEK_OF_MONTH=1,DAY_OF_MONTH=7,DAY_OF_YEAR=1,DAY_OF_WEEK=5,DAY_OF_WEEK_IN_MONTH=1,AM_PM=0,HOUR=0,HOUR_OF_DAY=4,MINUTE=27,SECOND=0,
		MILLISECOND=0,ZONE_OFFSET=0,DST_OFFSET=0]
	12:34:28,876 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	12:34:28,877 INFO  [STDOUT] Response Map is: {InternalObject=org.jboss.soa.esb.samples.quickstart.webservice_consumer_wise2.InternalObject@1627ba1
		[text=Jimbo,number=1], messageDate={month=3, day=7, year=2007},
		result=org.jboss.soa.esb.samples.quickstart.webservice_consumer_wise2.generated.ComplexObject@1ad067f,
		ExternalObject=org.jboss.soa.esb.samples.quickstart.webservice_consumer_wise2.ExternalObject@a2a890[
		internal=org.jboss.soa.esb.samples.quickstart.webservice_consumer_wise2.InternalObject@1627ba1[text=Jimbo,number=1],
		date=Sat Apr 07 00:00:00 BST 2007]}
	12:34:28,877 INFO  [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

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
        
        smooks-request-config.xml
        Smooks style configuration xml. Needed to configure the SmooksRequestMapper. 
        
        smooks-response-config.xml
        Smooks style configuration xml. Needed to configure the SmooksResponseMapper. 

	log4j.xml:
	Needed to configure log4J used by both the quickstart and the ESB itself. A listener needs a place to log.

	src/../SendEsbMessage.java:
	Send a message directly the the ESB internal JMS listener. Demonstrates how one can bypass the 
	gateway and speak directly to an ESB service.

        src/../SendJMSMessage.java:
	Send a message to JMS gateway listener and speak to an ESB service.
	
	src/../MyRequestAction.java
	Convert the message body into a webservice request parameter Map.
	
	src/../wise/SOAPClient.java
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
