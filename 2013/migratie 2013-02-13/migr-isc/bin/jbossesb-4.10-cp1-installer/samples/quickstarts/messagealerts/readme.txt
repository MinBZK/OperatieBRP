Overview:
=========
  The purpose of the messagealert quickstart sample is to show how the ESB is
  can set off warnings when a message exceeds size or processing time limits. 
  The ESB can monitor either services or actions for these message attributes.

  The threshold can be set on the jboss-esb.xml and can be changed later 
  within the jmx-console.      
  

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
