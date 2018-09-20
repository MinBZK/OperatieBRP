Overview:
=========
  This is a demonstration of a Hibernate listener. This test is configured 
  to use an hsqldb database (see jboss-esb.xml).  This 
  quickstart builds both an .esb and a .war.  The .war
  has a series of .jsps which demonstrate insert/delete/load/update.   

  If you browse the .war and insert items, you can see the Hibernate entities
  that you load and delete items, you can watch the Hibernate entities sent
  in messages to JBoss's console. 

  Here are the Hibernate Interceptor events which can be listened for:
    onCollectionRemove
    onCollectionUpdate
    onDelete
    onFlushDirty
    onLoad
    onSave

  See the sample jboss-esb.xml for how these are used within a
  <hibernate-message-filter>.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

  Installation instructions can be found in the install/readme.txt.

Setting Up 
======================= 
By default, this quickstart uses a standard hsqldb DB with "sa" as the
user and "sa" as the password.    You can easily change these settings by
changing the hibernate.cfg.xml file and the appropriate build.xml targets.    

This quickstart uses the same database settings that are required in the 
helloworld_sql_action quickstart.

To Run:
=======================
  1. Make sure your appserver is stopped.    If it is running, please 
     shutdown before installing this quickstart.
  2. In a command terminal window in the quickstart folder type 'ant deploy'.
  3. Start your appserver.    This quickstart deploys a jar into a common
     lib directory, so it is important that the app server be started 
     after deployment.    You may see JSP compilation errors if you try
     to deploy the quickstart for the first time while the appserver is 
     still running.
  4. Assuming that your appserver is running tomcat on port 8080, Browse to
     http://localhost:8080/hibernateaction/index.jsp
  5. Watch the events in the JBoss appserver console - by default this
     quickstart logs the onSave and onDelete actions.
  6. To undeploy the application, type 'ant undeploy'
