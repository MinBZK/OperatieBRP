Overview:
=========
  This quickstart demonstrates an SQL listener.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

  NOTE:
  1. This quickstart uses hsqldb so that it can be deployed without any 
	setup required.   
  2. "ant select" will show you the contents of the database table.   The
	<sql-message-filter> defined has a where-condition, so one of the rows
	that the table is populated with will never be processed.    There
	should be one remaining row for each time the table is populated.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.
