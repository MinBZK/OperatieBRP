Overview:
=========
  The purpose of the bpm_orchestration3 quickstart sample is to demonstrate the
  use jBPM to perform async calls into ESB services. This is a fairly advanced example.
  Make sure you are very comfortable with the bpm_orchestration1 and 2 before attempting 
  this one.  The provided process definition had a start, end and then three main nodes
  that include Service1, 2 and 3 asynchronously. After each invocation it goes into a wait-state.
  The process must then receive a signal.

To Run:
===========================
  Note: If running this quickstart on the App Server, you will need to ensure
        that the App Server JVM is started with enough PermGen space e.g:
           
           JAVA_OPTS="-Xms128m -Xmx512m -XX:PermSize=200M -XX:MaxPermSize=256m -Dorg.jboss.resolver.warning=true -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000"    

  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. type 'ant deployProcess'
  3. In Window1, type 'ant startProcess -Dtoken=Demo1'
     Switch back to Application/ESB Server console and look for
     [STDOUT] ** Begin Service 1 **
     [STDOUT] In : Hello start
     [STDOUT] Out: Hello start 'Service 1' 
     [STDOUT] ** End Service 1 **

  4. Enter the jBPM console (http://localhost:8080/jbpm-console/app/processes.jsf),
 	 and login (the username/password is admin/admin by default). Once logged in,
	 examine each process that you see using the "Examine" link and find the
	 "Instance ID" for the process that has a Key of "Demo1". 
     
  5. Using your favorite OSs File Explorer tool, look in the "bin" directory of your
     ESB container (either the full Application Server + ESB or ESB Server).  A file
     called replyToEPR.xml has been written to the disk by service1.groovy.  This file
     is the XML-ized version of the replyTo header that came "out" from EsbActionHandler,
     from the process flow.  Copy that file to the "build/resultdir" folder
     which is specified in the
     following section of the jboss-esb.xml:
     <fs-provider name="FSprovider1">
           <fs-bus busid="signalGwChannel" >
        			<fs-message-filter
        				directory="@RESULTDIR@"
         				input-suffix="replyToEPR.xml"          			
         				post-delete="true"
         				error-delete="true"
         			/>
       	  </fs-bus>
     </fs-provider>
    
     This file will be picked up and consumed by the Signal_Service which will use the
     ToCallBackService.groovy script to reconstitute the EPR, make it the To address
     of the message and use the ServiceInvoker deliver the message to the
     JBpmCallbackService. 
     
     Note: The STDOUT messages are from the 3 groovy scripts representing the logic of
     each service.

  6. To undeploy the esb archive, type 'ant undeploy' ("Window1").

Things to Consider:
===================
   - Review the process definition, specifically one of the nodes like Service 1:
	<node name="Service 1">
		<action name="esbAction"
			class="org.jboss.soa.esb.services.jbpm.actionhandlers.EsbActionHandler">
			<esbCategoryName>
				BPM_Orchestration3_Service1
			</esbCategoryName>
			<esbServiceName>Service1</esbServiceName>
			<bpmToEsbVars>
				<mapping bpm="theBody" esb="BODY_CONTENT" />
			</bpmToEsbVars>
			<esbToBpmVars>
				<mapping esb="BODY_CONTENT" bpm="theBody"/>
			</esbToBpmVars>
		</action>
		<transition to="Service 2"></transition>
	</node>
		
	The request is made asynchronously to the ESB Service and the process instance enters a wait-state.
	It goes to sleep and waits some external "signal" event occurs.  A waiting/sleeping
	process instance is held in the database. 
	
	Please review the "jBPM Integration" chapter in the document called: ServicesGuide.pdf
	http://anonsvn.jboss.org/repos/labs/labs/jbossesb/trunk/product/docs/ServicesGuide.pdf
	
	Tip: If you use the jbpm-console (http://localhost:8080) and the Graphical Process
	Designer provided by Red Hat Developer Studio then you can visually "see" the process
	moving from step to step to step with each command line signal.	
	
	
	
