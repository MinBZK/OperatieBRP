Overview:
=========
  The purpose of the deatletter quickstart sample is to show how messages end up in
  the DeadLetter Service. The interesting piece of configuration
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

  There are two actions. 
  
  src\org\jboss\soa\esb\samples\quickstart\deadletter\MyFailingSyncAction
    This action attempts a asynchronous call. The attempt should
    fail and the message should end up getting send to the DeadLetterService. By default
    this service stores a copy of the message in the MessageStore under the classification
    'RDLVR'. The message would have been redelivered at a later time (according to 
    the redeliver schedule defined in the jbossesb.esb/META-INF/jboss-esb.xml), but
    it is removed from the store or else the quickstart would get too confusing.
    Look for
    	Message in the RDLVR should be the same message: true
  
  src\org\jboss\soa\esb\samples\quickstart\deadletter\MyFailingAsyncAction
    This action attempts a synchronous call. The attempt should
    fail and the message should end up getting send to the DeadLetterService. By default
    this service stores a copy of the message in the MessageStore under the classification
    'DLQ'. An exception is thrown to stop any futher processing of the pipeline.
    Look for:
    	Message in the DLQ should be the same message: true
  
  src\quickstart\helloworld\test\SendJMSMessage.java:
    Shoots in the string passed in via the command line or in this case the arg
    attribute in the ant runtest task.

  src\quickstart\helloworld\test\SendEsbMessage.java:
    Shoots in the string passed in via the command line or in this case the arg
    atribute in the ant sendesb task.  This demonstrates how to build an "ESB
    aware" client that can invoke an ESB service.
