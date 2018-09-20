Quickstarts information:
========================

Before Running:
===============
  1. Make sure the JBoss Application server is running. Normally it is running
     on localhost or on 0.0.0.0 (INADDR_ANY), however if you have changed values
     in \server\default\conf\jndi.properties you will have to check in the
     appropriate location and update all jndi.properties files for the samples.
     See Getting_Started_Guide.pdf section 2.2.

It is often a good idea to copy the default server configuration to a different
name so that you can easily start a fresh one by simply deleting it. This also
makes it simple to diff both directories to find differences if problems occur. 
So, if you copied the default server configuration use the new configuration
name for the property 'org.jboss.esb.server.config' in
install/deployment.properties.

You can optionally copy the file conf/quickstarts.properties-example to
quickstart.properties if you want to run the quickstarts against a different
server then the one specified in step 1 above. This is also needed if you run
any of the quickstarts that use ftp.

To Run :
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In a command terminal window in this folder ("Window1"), type 'ant
      undeploy'.

More information about the quickstart can be found in the
Getting_Started_Guide.pdf document, which is in the doc folder.
