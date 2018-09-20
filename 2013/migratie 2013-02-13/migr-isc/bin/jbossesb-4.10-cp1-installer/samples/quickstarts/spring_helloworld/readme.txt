Overview:
=========
  The purpose of the spring_helloworld quickstart sample is to show the use of
  Spring based action invocations.  You can use a single Action class and make
  multiple method calls or use multiple Action classes.
  You can specify Spring bean XML definitions in jboss-esb.xml and lookup the
  beans in your Action class. Each Action loads a single Spring IoC container
  upon first spring bean invocation. Subsequent invocations will not load a new
  IoC container. Also, none of the Spring dependencies are included. You must
  manually add additional Spring dependency JARs to the classpath to create
  Spring beans that have special dependencies, like a DB connection pool.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.
  
Things to Consider:
===================
  Review the helloworld quickstart as the majority of this is similiar to
  that example

  MySpringEnabledAction.java:
    Has methods to display the inbound message, modify the message and to handle
    any exceptions which might be thrown in the action processing.
    MySpringEnabledAction will also lookup 2 different Spring beans located in 2
    different Spring config files and append the "greeting" to the message.
    Lastly, this action extends AbstractSpringAction. If you extend
    AbstractSpringAction, you can define spring configuration files in
    jboss-esb.xml and lookup Spring beans with
    "getSpringBeanFactory().getBean("someObject")".

  jbossesb.xml:
    There are 3 different actions. The first loads up a spring context, does a
    Spring bean lookup for "helloObject" and displays the JMS message payload
    plus the "helloObject.getGreeting()" value that is set in
    spring-context-hello.xml. 
  
    The second action will Spring lookup the "goodbyeObject" and append the 
    "goodbyeObject.getGreeting()" value to the message.
  
    The third action will Spring lookup the "helloObject" and "goodbyeObject"
    and append the "helloObject.getGreeting()" and "goodbyeObject.getGreeting()"
    value to the message. This last action demonstrates that a single action can
    lookup Spring beans that are in different Spring config files, so long as
    both config files are a comma separated list value for "springContextXml".

    <action name="sayHello" 
      class="org.jboss.soa.esb.samples.quickstart.helloworldaction.MyJMSListenerAction" 
      process="sayHelloSpring,displayMessage">
        <property name="exceptionMethod" value="exceptionHandler"/>
        <property name="springContextXml" value="spring-context-hello.xml"/>
    </action>
    <action name="addGoodbye" 
      class="org.jboss.soa.esb.samples.quickstart.helloworldaction.MyJMSListenerAction" 
      process="sayGoodbyeSpring,displayMessage">
        <property name="exceptionMethod" value="exceptionHandler"/>
        <property name="springContextXml" value="spring-context-goodbye.xml"/>
    </action>               
    <action name="addHelloGoodbye" 
      class="org.jboss.soa.esb.samples.quickstart.helloworldaction.MyJMSListenerAction" 
      process="sayHelloSpring,sayGoodbyeSpring,displayMessage">
        <property name="exceptionMethod" value="exceptionHandler"/>
        <property name="springContextXml" value="spring-context-goodbye.xml,spring-context-hello.xml"/>
    </action>    
  
    An ESB action class must have a constructor that accepts a ConfigTree
    argument like the following:
      public MySpringEnabledAction(ConfigTree config) { _config = config; } 
 
    A ConfigTree is an object holding any attributes associated with the action
    declaration in the jbossesb.xml
