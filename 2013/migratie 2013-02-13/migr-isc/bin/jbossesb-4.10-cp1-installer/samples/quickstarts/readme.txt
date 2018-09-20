Please run 'ant help-quickstarts' from any quickstart project or read
conf/readme.txt for information about running the quickstarts

The purpose of the quickstart samples is to illustrate various JBoss ESB product
features and general "how tos".

If you have any suggestions for future samples or simply have a suggestion for
improving on the ones contained here simply post on the JBoss ESB User Forum 
with "quickstart" in the subject line and a description of your thoughts.

Each quickstart has its own readme.txt file that should be reviewed carefully.
This can also be displayed by running 'ant help'. 
Run 'ant -p' to see which targets are available for each quickstart.

If you wish to run the quickstarts against a different server then the one that
you deployed to when you ran 'ant deploy' in the product/install directory then
you can modify this file to point to your specific version of JBoss AS.

org.jboss.esb.server.home=/opt/jboss/jboss5.1.0.GA
jboss.server=all


Prerequisites:
- Assumes you are comfortable working at the command line with Ant
- Assumes you are running JBossMQ or JBossMessaging on JBoss Application Server.
  Other JMS solutions should work as well but the configuration files contained
  in the quickstarts are focused on those.
- Assumes you have properly configured the JBoss ESB.  If you are using JBoss
  Application Server, simply go into the "install" folder for the JBoss ESB,
  modify the deployment.properties file and run "ant".  Make sure to have the
  App Server turned "off" while this process is occuring.
  This process will add the JBoss ESB artifacts to your JBoss Application
  Server.
- All of these examples use RMI-based registration.

The following is a brief outline of some of the various quickstarts. If this is
your first time working with the JBoss ESB we recommend at least trying
"helloworld", "helloworld_action" and "custom_action".

* helloworld - Uses a JMS Gateway combined with a Listener.  If this is your
  first time using the JBoss ESB then start by exercising this simple 
  "Hello World" example.

* helloworld_action - Builds on the concepts of the helloworld example but also
  show the use of multiple methods in a given action, how to "chain" action
  invocations into a particular sequence for a listener and how to respond
  (request/reply).  This example uses the notification feature.

* custom_action - demonstrates numerous tips & tricks to using the JBoss ESB
  actions. The first option shows what happens if you don't specify a "process"
  attribute.  The second option illustrates how to make multiple method calls of
  a since action class instance. The third option shows you how to create your
  own custom attributes and child elements for the action tag.

* transform_XML2POJO - Shows you how to setup a basic Smooks-based
  transformation to convert an XML document into Java POJOs.   

* simple_cbr - demonstrates how to use JBoss Rules and the Content-based Router
  Action for determining how a particular message should follow through the
  various services. This example also shows you that you can setup multiple
  services in a single esb-config.xml.

* fun_cbr - demonstrates the JBoss ESB's capability for hot deployment both of
  the CBR rules and the jbossesb.xml configuration file.

* business_service - JBoss ESB actions are custom mediators.  Their not
  specifically focused on business logic.  This example demonstrates how to
  invoke an EJB3 stateless session bean (plus how to build a deploy an EJB3
  SLSB on the JBoss AS). The SLSB is where your business logic can go.  Feel
  free to use your favorite middle-tier component for handling business logic
  (e.g. business validation, persistence).

* native_client - demonstrates how to create a 181 Web Service and use it as
  the front-end to the ESB.  This also demonstrates a synchronous call into the
  bus service.

* static_router - is primarily interesting because it is completely JMS-less.
  It uses FTP and File drop monitoring to move a message and send it out to
  multiple endpoints.

* aggregator - demonstrations how to use the Splitter/Aggregator capabilities of
  JBoss ESB.  It uses the Transformation engine as well as the static-routing
  feature.  It also demonstrations the use of multiple JVMs running different
  services.
  This is a fairly advanced demonstration.
