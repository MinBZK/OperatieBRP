Overview:
=========
  The purpose of the jms_secured quickstart is to show how JMS destinations 
  can be configured with security properties. 
  
  This quickstart is exactly like the basic helloworld quickstart except that 
  'jms-security-principal' and  'jms-security-credential' properties are 
  specified for the jms-bus elements.
  
  NOTE: this quickstart copies message-roles.properties and message-users.properties
  to ${org.jboss.esb.server.home}/server/${org.jboss.esb.server.config}/conf/props,
  and this will require a server restart to function correctly.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

NOTE to HornetQ Users:
========================
  You need to perform some additional manual configurations if running this 
  quickstart against an AS5 installation that has HornetQ installed:
  
  1. Add the following roles to the server/<name>/conf/props/hornetq-roles.properties:
     gatewayuser=gatewayrole
     esbuser=esbrole  
  
  2. Add the following to the server/<name>/conf/props/hornetq-users.properties:
     gatewayuser=gwpassword
     esbuser=esbpassword
  
  3. Add the following to the <security-settings> element in the server/<name>/deploy/hornetq/hornetq-configuration.xml:     
     <security-setting match="jms.queue.quickstart_jms_secured_Request_gw">
         <permission type="send" roles="esbrole,gatewayrole"/>
         <permission type="consume" roles="esbrole"/>
     </security-setting>      
     <security-setting match="jms.queue.quickstart_jms_secured_Request_esb">
         <permission type="send" roles="esbrole"/>
         <permission type="consume" roles="esbrole"/>
     </security-setting>

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.

What to look at in this Quickstart:
===================================
  1. src/org/jboss/soa/esb/samples/quickstart/jmssecured/test/SendJMSMessage
  	 Notice how the username(gatewayuser) and password for the destination 
  	 queue/quickstart_jms_secured_Request_gw  is specified during the creation of 
  	 the QueueConnection.
  2. jbm-queue-service.xml (JBM) or jbmq-queue-service.xml
  	 Notice how quickstart_jmssecured_Request_gw is defined with a SecurityConfig
  	 attribute and specifies two roles. One is a gatewayrole, which is used by the
  	 SendJMSMessage class to publish messages to the destination. The 'gatewayrole'
  	 can only write to the destination and not read. 
  3. jboss-esb.xml
     The message-filter for the jms-bus now specifies 'jms-security-principal' and
     'jms-security-credential'
  4. messaging-users.properties and messaging-roles.properties
	 Simple properties files which are used when JBoss Messaging is configured for property file 
	 based authentication. These files are copied to the conf/props directory of the jbossesb server 
	 in use.
  5. messaging-db-users.properties
	 Is a jboss service that insert users and roles into the JBoss Messaging database. This is only used when JBoss
	 Messaging is configured to use a database for managing users and roles.
  6. jmssecured.password
	 This is an encrypted password file for the configured JMS destination. This is configured in jboss-esb.xml instead of the 
     clear text password for the jmsbus with id 'quickstartGwChannel'.

	 The password was encrypted by issuing the following command (from the conf directory of your jboss server instance  (eg: default/conf):
     java -cp ../lib/jbosssx.jar org.jboss.security.plugins.FilePassword welcometojboss 13 esbpassword jmssecured.password

