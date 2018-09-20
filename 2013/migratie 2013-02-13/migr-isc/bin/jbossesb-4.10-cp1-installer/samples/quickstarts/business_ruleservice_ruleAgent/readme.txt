Overview:
=========
  Demonstrates the use of the BusinessRulesProcessor which uses facts that are 
  POJOs attached to an ESB Message.  The example uses Rules to calculate the price
  of an automobile insurance policy. In particular, this example gets the rules from 
  a rule package that is contained in the file system through the Drools RuleAgent.
  This rule package can also be retrieved from a URL created by the Drools BRMS.

  Make sure you have run simple_cbr, transformation_XML2POJO and fun_cbr
  quickstarts as their principles are used in this more complex example.

  Please note that this quickstart does not use Drools Signing when generating
  the compiled Drools package, please make sure that signing is not enabled in
  the server.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

  A comprehensive description of message transformation can be found in
  ServicesGuide.pdf, located in the docs/services folder.
  
  Notice the brmsdeployedrules.properties in the ruleAgent folder. This points
  the location of the rule package. It can specify a fully qualified file name,
  or just the directory (if there is only one package file in it). Modify one 
  of these to match the path of your ESB installation.
  The original drl that is located in the ruleAgent folder and named Original.drl.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.

What to look at in this Quickstart:
===================================
  
  The SendJMSMessage will send a message with the SampleDriver.xml included as a string.
  A Transformation service will convert the XML to POJOs (Driver and Policy), and put
  these objects in the message. The message is then sent to the PolicyPricing service
  which uses the brmsdeployedrules.property to access the rule package, creates a new
  stateless session, inserts the Driver and Policy from the message, and executes the 
  rules. The console will show the resulting price of the policy that is calculated 
  from the rules.
  
  
