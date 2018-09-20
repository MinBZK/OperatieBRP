Overview:
=========
  The purpose of the helloworld_action quickstart sample is to show the use of
  multiple action invocations from a single configuration.  You can use
  a single Action class and make multiple method calls or use multiple Action
  classes.

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

Project file descriptions:
==========================
  Review the helloworld quickstart as the majority of this is similiar to
  that example

  MyJMSListenerAction.java:
  Has methods to display the inbound message, modify the message and to handle
  any exceptions which might be thrown in the action processing.

  jboss-esb.xml:
  Includes a special action (from JBoss ESB core) that is used
  to send a message out of the ESB back to the non-"ESB aware" world. The
  gateway looks for inbound messages on "quickstart_helloworld_action_Request"
  which was configured by the "esb-quickstart-service.xml" file.
  
  The notifier sends the modified message to
    "quickstart_helloworld_action_Response":
  
  <action name="notificationAction" class="org.jboss.soa.esb.actions.Notifier">
    <property name="okMethod" value="notifyOK" />
    <property name="notification-details">
      <NotificationList type="OK"> 
        <target class="NotifyConsole" />
        <target class="NotifyQueues">
          <queue jndiName="queue/quickstart_helloworld_action_Response">
            <messageProp name="quickstart" value="hello_world_action" />
          </queue> 
        </target>
      </NotificationList> 
    </property>
  </action>
  
  An ESB action class must have a constructor that accepts a ConfigTree argument
  like the following:
    public MyJMSListenerAction(ConfigTree config) { _config = config; } 
 
  A ConfigTree is an object holding any attributes associated with the action
  declaration in the jbossesb.xml
