Overview:
=========
  The purpose of the bpm_orchestration1 quickstart sample is to demonstrate the
  integration of a process definition into JBoss ESB.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant deployProcess'.
  3. Switch back to "Window2", type 'ant startProcess'.
  4. Switch back to Application Server console to see the output from the ESB.
  
   Sample Output from "ant startProcess":
    ======================================
	12:21:41,781 INFO  [MessageSpy] Body: Start It Up
	12:21:42,156 INFO  [ProcessInfo] Token ID: null
	12:21:42,156 INFO  [ProcessInfo] Process ID: 1
	12:21:42,156 INFO  [ProcessInfo] Process Name: processDefinition2
	12:21:42,156 INFO  [ProcessInfo] Process Version: null
	12:21:42,156 INFO  [MessageSpy] Body: Start It Up
	12:21:42,187 WARN  [ProxyWarnLog] Narrowing proxy to class org.jbpm.graph.node.StartState - this operation breaks ==
	12:21:42,203 INFO  [STDOUT] Executed by the process, not by the ESB
	12:21:43,312 INFO  [STDOUT] 1********** Begin Service 1 ***********
	12:21:43,328 INFO  [STDOUT] In: Start It Up
	12:21:43,328 INFO  [STDOUT] Out: Service 1 Start It Up
	12:21:43,328 INFO  [STDOUT] ************ End Service 1 ************
	12:21:43,828 INFO  [STDOUT] 2********** Begin Service 2 ***********
	12:21:43,828 INFO  [STDOUT] In: Service 1 Start It Up
	12:21:43,828 INFO  [STDOUT] Out: Service 2 Service 1 Start It Up
	12:21:43,843 INFO  [STDOUT] ************ End Service 2 ************
	12:21:44,171 INFO  [STDOUT] 3********** Begin Service 3 ***********
	12:21:44,171 INFO  [STDOUT] In: Service 2 Service 1 Start It Up
	12:21:44,171 INFO  [STDOUT] Out: Service 3 Service 2 Service 1 Start It Up
	12:21:44,171 INFO  [STDOUT] ************ End Service 3 ************
	12:21:44,375 INFO  [STDOUT] Executed by the process, not by the ESB
	12:21:44,453 INFO  [MessageSpy] Body: Service 3 Service 2 Service 1 Start It Up
  
  5. In the http://localhost:8080/jbpm-console, you can check that the "processDefinition2" is
     deployed and that there is one running instance.
  6. To undeploy the esb archive, type 'ant undeploy' ("Window1").




