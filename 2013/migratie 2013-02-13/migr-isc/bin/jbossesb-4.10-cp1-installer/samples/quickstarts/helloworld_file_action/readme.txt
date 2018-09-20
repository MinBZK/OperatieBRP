Overview:
=========
  This is a basic example of using the File gateway feature of the JBoss ESB.
  Files that are found in a particular directory with a particular extension
  are sent to a JMS queue with actions for processing.

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
  This example demonstrates the use of a file gateway that by default loads the
  file and pushes into a JMS message queue. What follows is a more detailed
  discussion on the file gateway:

  * directory - the directory to be monitored for input file messages
  * input-suffix - the file extension to be monitored, other files will be
    ignored
  * work-suffix - the file extension that is used while the file is "in process"
    by the ESB.  The file is considered to be "in process" while the gateway is
    passing it into the ESB listener/service (in this case JMS queue).
  * post-delete - "true" or "false". The file can be removed once has been
    successfully processed.
  * post-directory - The place where the "processed" file ends up assuming no
    errors and assuming post-delete="false"
  * post-suffix - The file extension that is used to mark the file as
    "completed".
  * error-delete - "true" or "false". If there is an internal error and the file
    fails to be loaded by the ESB, delete it.
  * error-directory - The place to drop any file that fails to be
    uploaded/processed.
  * error-suffix - The file extension that is used to mark a file has had an
    internal error.
    Note: Error processing in this case means the file failed to pass through
    the gateway and into the waiting queue.
