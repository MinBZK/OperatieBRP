Overview:
=========
  This sample demonstrates how to deploy a JSR181 Webservice endpoint on
  JBossESB using the SOAPProcessor action. The sample also demonstrates 
  the usage of optional property jbossws-context to uniquely identify the
  correct endpoint. In the absence of this property the action will use the
  alternate endpoint and will fail.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
=======
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.
