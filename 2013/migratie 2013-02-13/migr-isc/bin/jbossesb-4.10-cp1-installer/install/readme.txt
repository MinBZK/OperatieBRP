The build.xml script in this directory is responsible for deploying the ESB
ant it's dependencies into the appropriate environment.  

Deployment into JBoss AS 4.2.2.GA
=================================
 - Install JBoss AS 4.2.2.GA into an appropriate location.
 - Copy deployment.properties-example to deployment.properties and configure
   the org.jboss.esb.server.home and org.jboss.esb.server.config properties
   to reflect the location of the application server and the required profile.
 - execute 'ant deploy'

JBossRemoting support in JBoss AS 4.2.2.GA
==========================================
If you are running JBossESB on JBoss AS 4.2.2.GA, you will need to update the
Application Server with JBossRemoting 2.2.2.SP2.  To do this, download the 
JBossRemoting 2.2.2.SP2 distribution from http://labs.jboss.org/jbossws/downloads/.

Unzip the distribution and copy "lib/jboss-remoting.jar" into the following folders
in your AS:
1.  lib
2.  server/<server>/lib


