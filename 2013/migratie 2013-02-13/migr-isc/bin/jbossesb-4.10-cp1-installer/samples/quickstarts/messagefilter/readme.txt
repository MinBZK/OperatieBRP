Overview:
=========
  The purpose of the messagefilter quickstart sample is to show how to filter
  a message based on content. Here we pass in an Order object and the FilterRules
  parse this object to looks for the quantity. If the quantity does not meet
  the threshold of 10, then the message is filter out. On the console you should
  see:
  
    [STDOUT] 9 Does not make the threshold
  
  In the build.xml you can change the default argument from '9' to '10' to see
  
    [STDOUT] 10 Makes the threshold
  
  In this case the message will get forwarded to the DemoService
  (service-category="Test" service-name="NextService") which prints out the
  message to the console.

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
