Overview:
=========
  The purpose of the custom_action quickstart example is to show different ways of
  using Action classes.

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
  jbossesb.xml:
    <action class="org.jboss.soa.esb.samples.quickstart.customaction.MyBasicAction"
            process="displayMessage" exceptionMethod="exceptionHandler" />    
    <action class="org.jboss.soa.esb.samples.quickstart.customaction.MyBasicAction"
            exceptionMethod="exceptionHandler" />
    <action class="org.jboss.soa.esb.samples.quickstart.customaction.StatefulAction"
            process="methodOne,methodTwo,displayCount"
            exceptionMethod="exceptionHandler" />
    <action class="org.jboss.soa.esb.samples.quickstart.customaction.CustomConfigAction"
            process="displayConfig" myStuff="rocks" moreStuff="rocks harder">
      <subElement1>Value of 1</subElement1>
      <subElement2>Value of 2</subElement2>
      <subElement3>Value of 3</subElement3>
    </action>

  The second MyBasicAction doesn't have a "process" attribute.  That means
  JBoss ESB is looking for a method with the following signature:
    public Message process(Message message) {

  The StatefulAction declaration has a process attribute that has a command
  delimited list of method names.  These methods will be called in the order in
  which they appear in the list. In addition, the StatefulAction class is only
  instantiated one time and all method calls are executed against the same
  instance.  When you execute this example you'll be able to see that the cnt
  maintains its state between method calls.  
  You should notice on the server console that the constructor is called when the .esb
  archive loads.

  The CustomConfigAction demonstrates how to access custom attributes on the
  main action tag and child elements of the action tag.  As an action developer
  you have full access to these items as a means of configuration for your
  action thus making them more reusable between various projects.
  
  If you wish to see a "native" call where there is no gateway - "ant sendesb". 
  Review the SendEsbMessage.java class in the "test" package.
  
  <action name="seventh"  class="org.jboss.soa.esb.samples.quickstart.customaction.CustomBeanConfigAction"> 
   		<property name="information" value="Hola Mundo" />
   		<property name="repeatCount" value="5"/>			   		
  </action>

  This demonstrates the use of the BeanConfiguredAction which takes properties defined in the jboss-esb.xml
  and calls the appropriate "setter" method.
  
  Add this line to the action chain in the jboss-esb.xml
  <action name="causesException" class="org.jboss.soa.esb.samples.quickstart.customaction.MyBasicAction"
 	     process="causesException"/> 
 
  It will allow you to see how exceptions are propagated. For more indepth examples on exception handling
  please review the exceptions_faults quickstart.
  
  
  
   	     
    
  
