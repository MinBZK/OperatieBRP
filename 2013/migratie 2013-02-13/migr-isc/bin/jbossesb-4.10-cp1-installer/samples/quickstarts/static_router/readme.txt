Overview:
=========
  This quick start will create a file on the FTP server and show the progress
  of the message through a static route.  Intermediate files will be created
  in the directories under build/dirs/

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

Prerequisites:
==============
  1. Make sure that you have write access to an FTP server.
  2. Make sure that the quickstarts.properties file contains the configuration
     information for the FTP hostname, user, password and base directory.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.
