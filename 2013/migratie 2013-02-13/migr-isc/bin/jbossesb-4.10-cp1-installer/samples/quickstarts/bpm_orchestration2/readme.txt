Overview:
=========
  The purpose of the bpm_orchestration2 quickstart sample is to demonstrate the
  use of JMS-based endpoints/services where the flow of execution is controlled
  by the process definition.  The process definition also includes a fork & join
  and makes each service invocation in a synchronous fashion.


To Run :
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant deployProcess'.
  3. In the command terminal window ("Window2"), type 
     'ant startProcess'
  4. Switch back to Application Server console to see the output from the ESB
  5. In this folder ("Window1"), type 'ant undeploy'.

Extra Credit:
=============
  1. Modify the process definition using your favorite editor or the Grahpical
     Process Desginer (from jBPM 3.2.x).  Remove the following transitions from
     the fork to Atlanta and Dallas:

       <transition name="tr2" to="Dallas WHSE"></transition>
       <transition name="tr3" to="Atlanta WHSE"></transition>

     This change means that the flow of execution will bypass Service6 and
     Service7.

  2. ant refreshProcess - This step copies the newly changed process definition
     to its appropriate location in the deployed .esb archive and then invokes
     the deployProcess command.

  3. ant startProcess - Service6 (Dallas) and 7 (Atlanta) are gone.

  Feel free to rewire the process definition in any order that you like to see
  the change in execution flow.

Things to Consider:
===================
  - BPM related quickstarts deploy as an exploded archive.
  - The service "logic" is coded in Groovy
  - Carefully review the jboss-esb.xml and the processdefinition.xml.  One of
    the key things to understand is how the ESB action invokes or interacts with
    the process and how it moves ESB message data into process instance
    variables.
    
    <property name="esb-to-jbpm">     
      <variables>
        <variable esb-name="esbMsgVar1" jbpm-name="processVar1" />
        <variable esb-name="BODY_CONTENT" jbpm-name="theBody" />
      </variables>
    </property> 

    esb-name maps to Message.getBody().get("esbMsgVar1")
    "BODY_CONTENT" maps to Message.getBody()

Sample Output from "ant deployProcess": 
======================================
13:14:07,359 INFO  [CommandExecutor] Process Definition 'bpm_orchestration2Process' is deployed.
13:14:07,396 INFO  [STDOUT] Process Definition Deployed: 
13:14:07,396 INFO  [STDOUT] [Hello World: Deploy the process def].

Sample Output from "ant startProcess":
======================================
21:22:17,333 INFO  [STDOUT] ** Begin Receive Order - Service 1 **
21:22:17,333 INFO  [STDOUT] In: Getting Started
21:22:17,336 INFO  [STDOUT] Out: Getting Started 'Receive Order' 
21:22:17,336 INFO  [STDOUT] ** End Receive Order - Service 1 **
21:22:17,531 INFO  [STDOUT] ** Begin Credit Check - Service 3 **
21:22:17,533 INFO  [STDOUT] In: Getting Started 'Receive Order' 
21:22:17,534 INFO  [STDOUT] Out: Getting Started 'Receive Order'  'Credit Check' 
21:22:17,534 INFO  [STDOUT] ** End Credit Check - Service 3 **
21:22:17,800 INFO  [STDOUT] ** Begin Validate Order - Service 2 **
21:22:17,801 INFO  [STDOUT] In: Getting Started 'Receive Order'  'Credit Check' 
21:22:17,801 INFO  [STDOUT] Out: Getting Started 'Receive Order'  'Credit Check'  'Validate Order' 
21:22:17,801 INFO  [STDOUT] ** End Validate Order - Service 2 **
21:22:17,994 INFO  [STDOUT] ** Begin Inventory Check - Service 4 **
21:22:17,995 INFO  [STDOUT] In: Getting Started 'Receive Order'  'Credit Check'  'Validate Order' 
21:22:17,996 INFO  [STDOUT] Out: Getting Started 'Receive Order'  'Credit Check'  'Validate Order'  'Inventory Check' 
21:22:17,996 INFO  [STDOUT] ** End Inventory Check - Service 4 **
21:22:18,287 INFO  [STDOUT] ** Begin Atlanta - Service 7 **
21:22:18,305 INFO  [STDOUT] In: Getting Started 'Receive Order'  'Credit Check'  'Validate Order'  'Inventory Check' 
21:22:18,305 INFO  [STDOUT] Out: Getting Started 'Receive Order'  'Credit Check'  'Validate Order'  'Inventory Check'  'Atlanta' 
21:22:18,306 INFO  [STDOUT] ** End Atlanta - Service 7 **
21:22:18,331 INFO  [STDOUT] ** Begin Dallas - Service 6 **
21:22:18,338 INFO  [STDOUT] In: Getting Started 'Receive Order'  'Credit Check'  'Validate Order'  'Inventory Check' 
21:22:18,339 INFO  [STDOUT] Out: Getting Started 'Receive Order'  'Credit Check'  'Validate Order'  'Inventory Check'  'Dallas' 
21:22:18,339 INFO  [STDOUT] ** End Dallas - Service 6 **
21:22:18,398 INFO  [STDOUT] ** Begin Los Angeles - Service 5 **
21:22:18,409 INFO  [STDOUT] In: Getting Started 'Receive Order'  'Credit Check'  'Validate Order'  'Inventory Check' 
21:22:18,410 INFO  [STDOUT] Out: Getting Started 'Receive Order'  'Credit Check'  'Validate Order'  'Inventory Check'  'Los Angeles' 
21:22:18,410 INFO  [STDOUT] ** End Los Angeles - Service 5 **
21:22:18,579 WARN  [HSQLDialect] HSQLDB supports only READ_UNCOMMITTED isolation
21:22:18,643 WARN  [HSQLDialect] HSQLDB supports only READ_UNCOMMITTED isolation
21:22:18,737 WARN  [HSQLDialect] HSQLDB supports only READ_UNCOMMITTED isolation
21:22:18,839 INFO  [STDOUT] ***** Ship It *****
21:22:18,841 INFO  [STDOUT] LA_Result: Getting Started 'Receive Order'  'Credit Check'  'Validate Order'  'Inventory Check'  'Los Angeles' 
21:22:18,841 INFO  [STDOUT] Dallas_Result: Getting Started 'Receive Order'  'Credit Check'  'Validate Order'  'Inventory Check'  'Dallas' 
21:22:18,841 INFO  [STDOUT] Atlanta_Result: Getting Started 'Receive Order'  'Credit Check'  'Validate Order'  'Inventory Check'  'Atlanta' 
21:22:18,842 INFO  [STDOUT] ***** End Ship It *****
21:22:18,866 INFO  [STDOUT] SUCCESS!: 
21:22:18,866 INFO  [STDOUT] [Getting Started 'Receive Order'  'Credit Check'  'Validate Order'  'Inventory Check'  'Los Angeles'  'Shipped' ].
