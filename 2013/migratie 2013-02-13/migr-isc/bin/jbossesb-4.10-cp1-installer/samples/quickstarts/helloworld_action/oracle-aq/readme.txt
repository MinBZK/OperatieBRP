To run with Oracle AQ as your JMS provider you will need to follow the following steps:

1. Using the Oracle Enterprise Manager Console create 3 queues named:
	ACTION_REQUEST, B and HELLO_WORLD_ACTION, make sure to set 
	the payload type to Object: SYS, AQ$_JMS_MESSAGE.

2. Edit the jboss-esb.xml file to reflect your database connection settings

3. Copy the jndi.properties and the jboss-esb.xml in the helloworld_action directory.

4. Follow the instructions in the readme.txt within lib/ext/jms/oracleaq and place the following jars in the
	helloworld_action/lib directory	

  asm.jar
  cglib-2.1_2jboss.jar
  mockejb.jar
  org.jboss.soa.esb.oracle.aq.<version>.jar
   
   and obtain the following jars from your Oracle DB install:
   
  aqapi13.jar  (from oracle)
  ojdbc14.jar  (from oracle)
   

Note that 

1. Oracle AQ is using a database connection as it's JMS connection.
2. Oracle AQ does not have a JNDI provider and it is common to register
   Queues in an LDAP. Here we chose to register the Queues to MockContext
   which is why you need the mockejb.jar, as well as the asm and cglib jars
   that it depends on. The org.jboss.soa.esb.oracle.aq.<version>.MR2.jar plugin
   does the actual work of registering. Registering to JNP proved not
   possible because of code in the Oracle API to support option 1, the LDAP
   registration.


The following can be used to create the queue table, create the queue, and enable the queue: 

exec dbms_aqadm.CREATE_queue_table(queue_table => 'myqueue_table_name', multiple_consumers => FALSE, queue_payload_type => 'SYS.AQ$_JMS_MESSAGE'); 

exec dbms_aqadm.CREATE_queue(queue_name => 'myqueue_name', queue_table => 'myqueue_table_name'); 

exec dbms_aqadm.start_queue( queue_name =>'myqueue_name' ,enqueue => true ,dequeue => true );