Overview:
=========
  The purpose of the groovy_gateway quickstart example is to demonstrate
  a service that has its message fed to it via a GUI console. This example
  includes a simple Groovy-based console that is automatically invoked when
  the example executes. You can then send in messages sync or asych.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

  Note:
  This quickstart creates a GUI console as part of the listener and, as such,
  requries access to a graphical display.  The server may be running in 'headless'
  mode, please check the run.conf or run.conf.bat and make sure that java.awt.headless,
  if configured, is set to false.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Switch back to Application Server console to see the output from the ESB
  3. The Message Injection Console will pop up automatically, enter some text and hit Send.
  4. In this folder ("Window1"), type 'ant undeploy'.
  
Things to Consider:
===================
  This is a fairly standard jboss-esb.xml with fairly basic Action classes.  The
  trick is this line in jboss-esb.xml
  <groovy-listener name="groovy-client" script="/scripts/MessageInjectionConsole.groovy" is-gateway="true" />
  Note: the MessageInjectionConsole.groovy will likely move to an "internal" JBoss ESB jar and out of 
  this particular project.
  The Groovy listener will execute any Groovy script upon service boot up.  It is expected into inject a message
  into the action pipeline.  
  
  This example is a nice tool for you to use to be able to interactive test your action chains. 
  
  Note: if you accidently close the Message Injection Console window, you might need to restart the component,
  not the entire ESB, just the .esb.  
