Overview:
=========
  The purpose of the camel_helloworld quickstart sample is to prove the
  integration of JBossESB + Apache Camel.

  This test configures a CamelGateway with Camel watching two directories for
  new files to be added, and upon detection, picks them up, converts them to ESB
  Messages, and invokes an ESB Service which prints out the contents of the
  files to the log.

  For more detailed information, please visit this wiki page:
  http://community.jboss.org/wiki/CamelGateway
  or refer to the Programmer's Guide.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
=======
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.

What to expect on "ant deploy":
===============================
Similar output will display in the Application Server console:

09:36:52,827 INFO  [JBoss4ESBDeployer] create esb service, Quickstart_camel_helloworld.esb
09:36:53,017 INFO  [CamelGateway] adding routes [
<routes xmlns="http://camel.apache.org/schema/spring">
  <route>
    <from uri="file://.../samples/quickstarts/camel_helloworld/build/input1?delete=true"/>
    <to uri="jbossesb://service?category=camel_helloworld&name=service1&async=false&timeout=30000"/>
  </route>
  <route>
    <from uri="file://.../samples/quickstarts/camel_helloworld/build/input2?delete=true"/>
    <to uri="jbossesb://service?category=camel_helloworld&name=service1&async=false&timeout=30000"/>
  </route>
</routes>
]
09:36:53,347 INFO  [DefaultCamelContext] Apache Camel 2.4.0 (CamelContext: camel-1) is starting
09:36:53,347 INFO  [DefaultCamelContext] JMX is disabled. Using DefaultManagementStrategy.
09:36:53,999 INFO  [AnnotationTypeConverterLoader] Found 3 packages with 13 @Converter classes to load
09:36:54,021 INFO  [DefaultTypeConverter] Loaded 143 type converters in 0.635 seconds
09:36:54,122 INFO  [DefaultCamelContext] Route: route1 started and consuming from: Endpoint[file://.../samples/quickstarts/camel_helloworld/build/input1?delete=true]
09:36:54,122 INFO  [DefaultCamelContext] Route: route2 started and consuming from: Endpoint[file://.../samples/quickstarts/camel_helloworld/build/input2?delete=true]
09:36:54,122 INFO  [DefaultCamelContext] Started 2 routes
09:36:54,122 INFO  [DefaultCamelContext] Apache Camel 2.4.0 (CamelContext: camel-1) started in 0.775 seconds

What to expect on "ant runtest":
================================
Similar output will display in the Application Server console:

09:37:35,194 INFO  [STDOUT] Message structure: 
09:37:35,194 INFO  [STDOUT] [Hello user! (2)].
09:37:35,196 INFO  [STDOUT] Message structure: 
09:37:35,196 INFO  [STDOUT] [Hello user! (1)].

What to expect on "ant undeploy":
================================
Similar output will display in the Application Server console:

09:38:09,249 INFO  [DefaultCamelContext] Apache Camel 2.4.0 (CamelContext:camel-1) is shutting down
09:38:09,249 INFO  [DefaultShutdownStrategy] Starting to graceful shutdown 2 routes (timeout 300 seconds)
09:38:09,261 INFO  [DefaultShutdownStrategy] Route: route2 suspended and shutdown deferred, was consuming from: Endpoint[file://.../samples/quickstarts/camel_helloworld/build/input2?delete=true]
09:38:09,261 INFO  [DefaultShutdownStrategy] Route: route1 suspended and shutdown deferred, was consuming from: Endpoint[file://.../samples/quickstarts/camel_helloworld/build/input1?delete=true]
09:38:09,262 INFO  [DefaultShutdownStrategy] Route: route2 shutdown complete.
09:38:09,262 INFO  [DefaultShutdownStrategy] Route: route1 shutdown complete.
09:38:09,262 INFO  [DefaultShutdownStrategy] Graceful shutdown of 2 routes completed in 0 seconds
09:38:09,280 INFO  [DefaultInflightRepository] Shutting down with no inflight exchanges.
09:38:09,281 INFO  [DefaultCamelContext] Uptime: 1 minute
09:38:09,281 INFO  [DefaultCamelContext] Apache Camel 2.4.0 (CamelContext: camel-1) is shutdown in 0.032 seconds
09:38:09,321 WARN  [ServiceMessageCounterLifecycleResource] Calling cleanup on existing service message counters for identity ID-7

