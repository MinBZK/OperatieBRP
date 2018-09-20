Requirements :
========
  Ant 1.7 is required to run this quickstart.      
 
  You will also have to edit server.properties to point it at your application
  server.

Overview:
=========
  The purpose of the two_servers quickstart sample is to demonstrate 
  running two versions of the ESB server (or the JBoss AS).

  The sample bindings depend on a sample-bindings.xml, which spells out 
  which ports are used for different services.   Each binding depends on a port
  profile (in this example, ports-01 and ports-02), which is then referenced
  in the server's jboss-service.xml (see $JBOSS_HOME/server/first/conf/jboss-service.xml
  and $JBOSS_HOME/server/second/conf/jboss-service.xml).    

  This quickstart is a bit different than others in that it will not deploy .esb
  packages to the config specified in your deployment.properties file, but will 
  create two configs based on the server.properties file in this directory.     

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

Important
=========
  This quickstart is intended to be run with embedded databases.  If you have altered
  the default configuration to use a remote database then this setup will be duplicated,
  and therefore shared, by both of the generated configurations.  It may be necessary to
  make additional changes to the configuration of those components which are sharing
  a database, for example if JBoss Messaging is the current JMS provider then a unique
  ServerPeerID must be configured within the messaging-service.xml configuration file.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), 
     Type 'ant setup-servers'.    
	(Note that this will remove both the SERVER_HOME/server/first and SERVER_HOME/server/second directories, and will make a copy of the default directory in each of these locations.   This target requires Ant 1.7 or higher)
  2. In Window 1, ant deploy
  3. If HornetQ is the installed JMS Provider:
       a) Open server/second/hornetq/hornetq-configuration.xml
       b) Search for all "port" parameters (<param key="port") and modify the port number by adding
          a predetermined offset e.g. port number 5445 to 10445, port number 5455 to 10455 etc.
  4. In Window 2, if AS 4.x: start the first application server (./run.sh -c first)
                  if AS 5.x: start the first application server (./run.sh -c first -Djboss.service.binding.set=ports-01)
  5. In Window 3, if AS 4.x: start the second application server (./run.sh -c second) 
                  if AS 5.x: start the second application server (./run.sh -c second -Djboss.service.binding.set=ports-02)
  6. In Window 1, ant runtest
