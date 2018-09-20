Overview:
=========
  The purpose of the load generator quickstart to provide a good load testing 
  framework for ESB services. The files in this quickstart provide good TPS, Average TPS
  and load simulation for ESB services.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

Protected JMX 
=======================
  If access to JMX is secured on your server, after deploying this quickstart
  you will need to run the "ant runtest-secure" target, which takes a username
  and a password, instead of the "ant runtest" target. 

To Run '.esb' archive mode:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest' or 'ant runtest-secure'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.

Load Test Targets
===========================
runtest - Runs the load agent to simulate load and the load reporter to report on load
runtest-secure - Runs the load agent to simulate load, and the load reporter authenticates to secure http
load-agent - Simulates load only
load-report - Report on load only

NOTE: "runtest" will run the agent in a daemon thread, then start the reporter. This is 
a good target for running tests, but if there's a problem with JMSLoadAgent.groovy, you will
not see it. Use the "load-agent" target to troubleshoot the agent script/properties. 

Key Files
===========================
./scripts/load.xml (.default/.hornetq)
This is where you specify the properties of the load script and reporter. Things like
the number of messages, throttling of load, services to report and more are listed here
so you don't have to edit the groovy scripts.

./scripts/JmsLoadAgent.groovy
Simulates load by popping messages to a JMS Queue. Use load.properties to specify the JMS
queue and the throttling of messages. This script is only good for JMS load simulation,
but it can be used as a template for other types of simulation, like file drops.

./scripts/LoadReport.groovy
Reports load on services specified in load.xml. It will write output to the console and
to the file system. The output is CSV based for now, but it's very easy to customize it in the
groovy script.

./scripts/payload.xml
Payload that the load agent will use for JMS messages. If testing CBR or other payload sensitive services
make sure that the message content is valid. You can specify any payload file in load.properties.

./jboss-esb.xml
This file has 2 services, DummyJMSLoad and DummyJMSLoad2. DummyJMSLoad processes faster than DummyJMSLoad2
and it has a router to the DummyJMSLoad2 ESB listener queue. The use of the router is meant to demonstrate
how one can monitor TPS of a service chain, not just a single service.




