Overview:
=========
  The purpose of the monitoring_action quickstart sample is to show
  the use of the MVELMonitoringAction.     This action will allow you to use
  MVEL patterns and expressions parsed through beanshell in order to store
  information from your messages. 

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

  NOTE:
  After running the quickstart, (ant runtest), go to jmx-console: http://localhost:8080/jmx-console/
  You should be able to find the object :
 
  jboss.esb:category=MVELMonitor,deployment=Quickstart_monitoring_action.esb,service-name=SimpleListener
 
  Two of the messages should meet the expression and will be stored in the bean.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.
