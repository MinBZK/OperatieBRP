Overview:
=========
  The purpose of the spring_aop quickstart sample is to show the use of Spring
  AOP.   You can specify Spring bean XML definitions in jboss-esb.xml and lookup
  the beans in your Action class. Each Action loads a single Spring IoC
  container upon first spring bean invocation. Subsequent invocations will not
  load a new IoC container. Also, none of the Spring dependencies are included.
  You must manually add additional Spring dependency JARs to the classpath to
  create Spring beans that have special dependencies, like a DB connection pool.

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
    There is one action method called "sayHelloAopStyle". The method gets
    triggered when a JMS message is sent. "sayHelloAopStyle" actually uses the
    message payload to get the greeting, but the interceptor will change the
    greeting.

  jboss-esb.xml
    The method, "sayHelloAopStyle" uses a spring interceptor to change the
    greeting message.  Notice that the action does not have any println
    statements, "MyInterceptor" is doing all of the console output work. The
    interceptor is configured in spring-context.xml, not annotation style. You
    can change the greeting in build.xml. The greeting is sent to the ESB via
    JMS.

    <action name="sayHelloAopStyle" 
      class="org.jboss.soa.esb.samples.quickstart.helloworldaction.MySpringEnabledAction" 
      process="sayHelloAopStyle">
        <property name="exceptionMethod" value="exceptionHandler"/>
          <property name="springContextXml" value="spring-context.xml"/>
    </action>  

    An ESB action class must have a constructor that accepts a ConfigTree
    argument like the following:
      public MyJMSListenerAction(ConfigTree config) { _config = config; } 
 
    A ConfigTree is an object holding any attributes associated with the action
    declaration in the jbossesb.xml
