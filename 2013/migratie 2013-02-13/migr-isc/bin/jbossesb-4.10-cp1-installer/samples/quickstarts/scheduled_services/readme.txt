Overview:
=========
  The purpose of the scheduled_services quickstart example is to demonstrate
  a service that is time triggered.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Switch back to Application Server console to see the output from the ESB
  3. In this folder ("Window1"), type 'ant undeploy'.
  
Things to Consider:
===================
  In jboss-esb.xml
  <scheduled-listener name="my_scheduled_listener"
	scheduleidref="3-sec-trigger"
	event-processor="org.jboss.soa.esb.samples.quickstart.scheduler.MyScheduledActionMsgComposer" />
	
	MyScheduledActionMsgComposer is a class that you develop that allows you to 
	create an ESB Message for the action pipeline.  Your composer might pull data
	in from an RSS feed, an email account or particularly any resource and then convert
	that data into an ESB Message.
