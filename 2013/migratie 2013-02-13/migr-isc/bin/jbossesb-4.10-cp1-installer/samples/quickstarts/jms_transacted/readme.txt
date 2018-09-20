Overview:
=========
  The purpose of the jms_transacted quickstart is to show how the JMS transport 
  in the ESB can be configured to use transactions and how redelivery can be 
  accomplished by taking advantage of the JMS transport.

  For details about this quickstart see the 'What to look for in this Quickstart" section.

  This quickstart uses jms-jca-provider and more information about jms jca can
  be found here: http://wiki.jboss.org/wiki/Wiki.jsp?page=UsingJCAWithJBossESB
  
Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type:
     'ant deploy'
     This will deploy the quickstart
  2. Run the quickstart:
     'ant runtest'
  3. Switch back to Application Server console to see the output from the ESB
  4. Undeploy the quickstart:
     'ant undeploy'

What to look for in this Quickstart:
===================================

  1. Overall flow of this quickstart:
	* The first time we enter the action pipleline, we insert a row into the database: (server console output below)
	    [DBInsertAction] Successfully inserted [Hello Transacted JMS World] counter[1]] into jms_transacted_table	

	* Next, the ThrowExceptionAction is called. 
	  This action can be configured with the number of times the action
          should throw an exception to rollback the transaction (jboss-esb.xml):
	    <property name="rollbacks" value="5"/>

	  In the cases where the exception is thrown, this will result in the
          JCA adapter rolling back the transaction.  As a consequence the
          database insert in the preceding action will also be rolled back.

          The console output will display an error message from the
          ActionProcessingPipeline followed by a stacktrace of the
          generated exception.  This exception is then propagated to the
          JCA/JMS adapter which will also document the issue.

          Part of the console output follows:
           [ActionProcessingPipeline] Unexpected exception caught while
           processing the action pipeline:
           java.lang.IllegalStateException: [Throwing Exception to trigger a
           transaction rollback]
           [JmsServerSession] Unexpected error delivering message
           delegator->JBossMessage[512]:PERSISTENT, deliveryId=1
           java.lang.IllegalStateException: [Throwing Exception to trigger a
           transaction rollback]

	  After the configured number of rollbacks has been reached, the action
          will complete normally allowing the transaction to commit.

          [STDOUT] JMS Transacted Quickstart entered. Message body: 
          [STDOUT] [Hello Transacted JMS World]].
          [DBInsertAction] Successfully inserted [Hello Transacted JMS World]
          counter[6]] into jms_transacted_table
          [STDOUT] JMS Transacted Quickstart processed successfully. Message body: 

	* Only the last insert will be committed to the database when the action pipeline successfully completes.
	  In the console output window where 'ant runtest' was run from, the output of the database table is displayed:
	    [echo] Select * from jms_transacted_table
	    [sql] Executing commands
	    [sql] DATA_COLUMN
	    [sql] Hello Transacted JMS World] counter[5]
            [sql] Hello Transacted JMS World] counter[6]
	    [sql] 
	    [sql] 0 rows affected
	    [sql] 1 of 1 SQL statements executed successfully
	  This shows that only the last insert was committed. The others were never written to the database since they were
	  rolled back.

	  The value of counter in 'Hello Transacted JMS World] counter[6]' should always match the value in the servers console for 
	  the last successfully completed execution of the action pipeline:
	    [DBInsertAction] Successfully inserted [Hello Transacted JMS World] counter[6]] into jms_transacted_table
	    [STDOUT] JMS Transacted Quickstart processed successfully: 
	    [STDOUT] [Hello Transacted JMS World]].

  2. org.jboss.soa.esb.samples.quickstart.jmstransacted.test.DBInsertAction
     Inserts the contents of the ESB Message object into the database table by using the sql statement defined in 
     the property 'db-insert-sql'.
     This class contains a counter that is incremented for each call. This counter is added to the text inserted into the table. 
     The string looks like this: [Hello Transacted JMS World] counter[1]] 
     This is done to seperate the inserts and visually verify that the correct data is committed to the database. 
	  
  3. org.jboss.soa.esb.samples.quickstart.jmstransacted.test.ThrowExceptionAction
     The current transaction is rolled back as a result of the action
     handler throwing a RuntimeException (or a subclass of such) during
     the processing method.  The exception is caught by the
     ActionProcessingPipeline but, as it is not identified as an application
     exception, it will be propagated to the caller (in this case the
     JMS/JCA adapter).  The JMS/JCA adapter will then force the enclosing
     transaction to rollback, resulting in the redelivery of the message.

  4. jboss-esb.xml
     The message-filter for the jms-bus-filter now specifies 'transacted' attribute.
     Notice the "dlQMaxResent" activation-config property:
       <activation-config>
          <!-- The maximum number of times a message is redelivered before it is sent to the DLQ -->
          <property name="dLQMaxResent" value="5"/>
       </activation-config>

     For activation-config properties see : http://www.jboss.org/wiki/Wiki.jsp?page=ConfigJMSMessageListener

  5. jbm-queue-service.xml
     Notice the following attribute:
	  <attribute name="MaxDeliveryAttempts">15</attribute>
     MaxDeliveryAttempts must be greater then dLQMaxReset or it will take priority over dLQMaxReset.

	 The redelivery attempt delay is specified by the property 
      <attribute name="RedeliveryDelay">10000</attribute>


