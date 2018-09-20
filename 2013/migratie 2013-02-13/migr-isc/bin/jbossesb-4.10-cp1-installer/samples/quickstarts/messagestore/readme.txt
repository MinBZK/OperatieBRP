Overview:
=========
  The purpose of the messagestore quickstart sample is to show how to store
  a message in the MessageStore. The only interesting piece of configuration
  is:

  <!-- Add a copy of the message to the message store under categorization
      'test' -->
  <action name="PersistAction" class="org.jboss.soa.esb.actions.MessagePersister" >
    <property name="classfication" value="test"/>
    <property name="message-store-class"
     value="org.jboss.internal.soa.esb.persistence.format.db.DBMessageStoreImpl"
     />
  </action>

  Which causes a message to be saved in the DB messageStore under the
  categorization 'test'. Tooling will be added to inspect the messageStore.

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

What to look at in this Quickstart:
===================================

  src\quickstart\CheckMessageStoreAction.java:
    The action class that is identified in the esb-config.xml file and is called
    whenever a message is received.

  src\quickstart\helloworld\test\SendJMSMessage.java:
    Shoots in the string passed in via the command line or in this case the arg
    attribute in the ant runtest task.

  src\quickstart\helloworld\test\SendEsbMessage.java:
    Shoots in the string passed in via the command line or in this case the arg
    attribute in the ant sendesb task.  This demonstrates how to build an "ESB
    aware" client that can invoke an ESB service.
