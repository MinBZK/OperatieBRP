Overview:
=========
  This is a very basic example that demonstrates how to configure the FTP
  gateway so that it picks up messages from an ftp server, routes them through
  a JMS queue and finally to your action class for processing. 
  
Pre-requisites
==============
  This quickstart includes a demonstration of a setup for processing remote
  files on an ftp server without renaming.  This ability relies on a distributed
  cache provided by jgroups and, as a consequence, can only run when targetting
  a server containing jgroups.jar within the lib directory.

  The 'readonly' targets reference an assertion test which checks for the
  existence of this jar file.  If the jar is not present then please adjust
  your configuration to target an instance which contains the jar.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

  Make sure that the quickstarts.properties file contains the configuration
  information for the FTP hostname, username, password and directory.

  Make sure that you have write access to the FTP server.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.

To Run as a .esb archive readonly version:
==========================================
  1. In a command terminal window in this folder ("Window1"), type
     'ant deploy-readonly'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.
   
What to consider in this Quickstart:
===================================  
  1. The FTP configuration has been initialised in
     ../conf/quickstarts.properties

  2. The FTP directory is accessable and can be written to as the "in process"
     and "completed" files are renamed accordingly. 
  
     If you do not have write access you will receive an error that looks
     something like the following:
       [java] 20:41:36,625 ERROR [Thread-5][AbstractFileGateway] Problems
         renaming file new_file.dat to new_file.dat.esbWorking

  3. Running the read-only version: 
     Note that the database used by the cacheloader is hypersonic and it is an
     inmemory database. This means that if you close all the ESBs
     running('ant run-readonly') windows all files will be retrieved once more.
     This would not be the case with a persistent database.

Using the Apache FTP Server:
============================
If running from source, you can avail of the Apache FTP Server that's available in source.

After deploying the Apache FTP Server, you will need to update the connection details in
the quickstart.properties file.  Just add the following:
	jbossesb.ftp.hostname=localhost
	jbossesb.ftp.username=esb
	jbossesb.ftp.password=esb
	jbossesb.ftp.directory=
