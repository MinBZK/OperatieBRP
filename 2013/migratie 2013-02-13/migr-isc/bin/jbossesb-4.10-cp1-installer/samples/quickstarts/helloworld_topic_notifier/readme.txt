Overview:
=========
  The purpose of the helloworld topic notifier quickstart sample is to 
  show how to use the Notifier to pass messages to a topic.   This QuickStart 
  sends a message to a queue and the Notifier will pass it to the topic using
  NotifyTopics.      A small utility is included that listens to the topic.
  
  If you wish to view the messages sent to the jmx-console, attach a durable
  subscriber by using the "ant receive-durable" target, and then find the 
  subscription id by invoking the "listAllSubscriptionAsHTML()" method.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed description of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant receive'. 
  3. Open another command terminal window in this folder ("Window3"), type
     'ant runtest'.
  4. Switch back to Application Server console to see the output from the ESB
  5. In this folder ("Window1"), type 'ant undeploy'.
