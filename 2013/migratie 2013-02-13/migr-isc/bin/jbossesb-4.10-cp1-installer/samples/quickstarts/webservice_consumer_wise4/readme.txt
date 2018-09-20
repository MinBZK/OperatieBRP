Overview:
=========
    This quickstart demonstrates how to use SmookHandler and JAX-WS hander in Wise SOAPClient to transform and manipulate
    soap message. 

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
    1.  Open wise-core.properties and modify the property 'wise.tmpDir' there to suit your environment.   
    2.  In a command terminal window in this folder ("Window1"), type 'ant deploy'.
    3.  Open another command terminal window in this folder ("Window2"), type 'ant runtest'.
    4.  Switch back to Application Server console to see the output from the ESB
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
        
        src/../MyCustomLoggingHandler.java
        JAX-WS hanlder file. It is used to display the soap message to the console.
        
        src/../MyCustomLoggingHandler2.java
        Another JAX-WS handler file.  
	
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
