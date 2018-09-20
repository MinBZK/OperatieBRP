Overview:
=========
  The purpose of the dynamic router list quickstart sample is demonstrate
  the recipient list EIP pattern (as defined here:
  http://www.enterpriseintegrationpatterns.com/DynamicRouter.html

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.


To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. In a command terminal window in this folder ("Window2"), type
     'ant receive-destination1'.
  3. In a command terminal window in this folder ("Window3"), type
     'ant receive-destination2'.
  4. In a command terminal window in this folder ("Window4"), type
     'ant receive-destination3'.
  5. In "Window1", type 'ant runtest'.
  6. Switch back to the Application Server console, "Window2", "Window3" and
     "Window4" to see the output from the ESB
  7. When finished, interrupt the receivers using Ctrl-C.
  8. Undpeloy the esb archive, type 'ant undeploy'.

Things to Consider:
===================
  You can modify the argument defined in build.xml to control which channels
  receive the messages. A value other than ":OK" will result in a destination
  instance not receiving messages through the router. Stopping and starting the
  destination app (for example: ant receive-destination1) instance will cause
  the dynamic router rule base to be updated. 
  
  The contents of the Hashtable - this is persisted in a serialized object
  written to a file in the server's bin directory - that holds the dynamic
  router's rule base is displayed on the server's stdout when it is changed.
  For example:
  
  [STDOUT] Writing Hashtable to disk
  [STDOUT]     Hashtable entry: key = queue/qsdynamicrouter_1_Request, value = OK
  [STDOUT]     Hashtable entry: key = queue/qsdynamicrouter_2_Request, value = not_OK
  [STDOUT]     Hashtable entry: key = queue/qsdynamicrouter_3_Request, value = OK

  The quickstart makes use of the queues illustrated (the queues are shown 
  in boxes) in the following diagram. You might review the build.xml file to 
  see how it is setup. More details on the build.xml can be found later in 
  this document.

      application initiated
               |
               |
               V
  -------------------------------            --------------------------- 
  |qsdynamicrouter_Input Request|            |qsdynamicrouter_1_Request| -------> destination1 -> |
  -------------------------------            ---------------------------                          |
               |                          /                                                       |
               |                         |                                                        |
               |                         |   ---------------------------                          |
        Message Router  ---------------->|   |qsdynamicrouter_2_Request| ------>  destination2 -> |
               ^                         |   ---------------------------                          | 
               |                         |                                                        |
               |                         \                                                        |
      Dynamic Rules Base                  \ ---------------------------                           |
               ^                             |qsdynamicrouter_2_Request| ------>  destination3 -> |
               |                             ---------------------------                          |
               |                                                                                  |
               |                                                                                  |
               |                             -------------------------------                      |
               |---------------------------- |dynamicrouter_Control_Request| <--------------------|
                                             ------------------------------- 
