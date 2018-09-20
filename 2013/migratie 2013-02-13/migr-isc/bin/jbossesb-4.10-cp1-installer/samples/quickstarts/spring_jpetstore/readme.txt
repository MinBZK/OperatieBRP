Overview:
=========
  The purpose of the spring_jpetstore quickstart sample is to show advanced use
  of Spring based action invocations.  You can leverage any application logic,
  like the business logic tier of a spring based web application by looking up
  the business service bean and executing a method. This is a complex usage of
  Spring that includes AOP, iBatis, a datasource and transaction management.
  
  You can specify Spring bean XML definitions in jboss-esb.xml and lookup the
  beans in your Action class. Each Action loads a single Spring IoC container
  upon first spring bean invocation. Subsequent invocations will not load a new
  IoC container.  In this example many Spring dependencies are included and used.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. Open a command terminal window in this folder ("Window1").
  2. In "Window1", type 'ant start-hsqldb-server'.
  3. Open another command terminal window in this folder ("Window2").
  4. In "Window2", type 'ant clean deploy'.
  5. In "Window2", type 'ant runtest'.
  6. Switch back to Application Server console to see the output from the ESB
  7. Interrupt the database ("Window1") using Ctrl-C.
  8. In this folder ("Window1"), type 'ant undeploy'.

Project file descriptions:
==========================

  InsertOrderAction.java:
    Takes the sample order XML from the ESB message then converts it into an
    Order POJO. The order POJO is then inserted into the Spring JPetstore
    database by calling the "PetStoreFacade.insertOrder()" method. The
    "PestoreFacade.insertOrder()" method uses a SpringAOP transaction advice. 
  
    After successful insert, the InsertOrderAction will query the database, by
    using "PetStoreFacade.getOrdersByUsername()". and display the Orderd details
    in the console.

  jbossesb.xml:
    Make sure that the Spring application context files are defined via the
    "springContextXml" property.

    <action name="insertOrderAction" 
           class="org.jboss.soa.esb.samples.quickstart.spring.InsertOrderAction" 
           process="insertOrder">
      <property name="exceptionMethod" value="exceptionHandler"/>
      <property name="springContextXml"
                value="applicationContext.xml,dataAccessContext-local.xml"/>
    </action>
  
    An ESB action class must have a constructor that accepts a ConfigTree
    argument like the following:
      public MyJMSListenerAction(ConfigTree config) { _config = config; } 
 
    A ConfigTree is an object holding any attributes associated with the action
    declaration in the jbossesb.xml
