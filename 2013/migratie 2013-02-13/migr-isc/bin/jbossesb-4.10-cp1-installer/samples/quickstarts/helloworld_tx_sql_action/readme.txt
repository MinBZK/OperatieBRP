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
  3. run 'ant create' after deploying.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.

Note, when running you will see something like:

&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& 
12:24:28,139 INFO [STDOUT] DATA READ: data 22 
12:24:28,140 INFO [STDOUT] column DATA_COLUMN = <data 22>column TIMESTAMP_COL = <null>column UNIQUE_ID = <2> 
12:24:28,140 INFO [STDOUT] &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& 
12:24:28,140 INFO [STDOUT] Will rollback transaction. Expect to see record again! 
12:24:28,140 INFO [STDOUT] BAD READ ON DATA! 

You can safely ignore the 'BAD READ ON DATA!' as this is expected behaviour.
