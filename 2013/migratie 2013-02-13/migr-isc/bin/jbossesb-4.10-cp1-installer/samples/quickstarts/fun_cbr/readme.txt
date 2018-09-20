Overview:
=========
  This example has three "teams" of order processors.  All new orders that flow
  into the system can be evaluated via the XPath or Drools rule processors.
  These routing rules will determine which team receives the order for actually
  processing.   In the case of the green team, the order is first transformed to a
  different format prior to routing.
  
  This quickstart also show how namepaces can be used with xpath expressions. 


Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. In a command terminal window in this folder ("Window2"), type
     'ant receiveBlue'.
  3. In a command terminal window in this folder ("Window3"), type
     'ant receiveRed'.
  4. In a command terminal window in this folder ("Window4"), type
     'ant receiveGreen'.
  5. In "Window1", type 'ant runtestXPath' (or 'ant runtestDrools'). See the build.xml
     and jboss-esb.xml files.
  6. Switch back to the Application Server console, "Window2", "Window3" and
     "Window4" to see the output from the ESB
  7. When finished, interrupt the receivers using Ctrl-C.
  8. Undpeloy the esb archive, type 'ant undeploy'.

Things to Consider:
===================
  The routing depends on the statusCode attribute in the SampleOrder.xml file.
  It would be relatively simple to modify this example to include a service
  (listener & action combination) to introduce "scoring" rules logic that sets
  the status code of the message prior to sending it on to the CBR.
  
  Change the statusCode to select another destination
   0 - Blue
   1 - Red
   2 - Green
   
  Extra Credit: Open FunCBRRules-XPath.drl and change the routes to the following:
		Blue for statusCode="1"
		Red for statusCode="0"
		Green for statusCode="2"
  Then "ant runtest" again, the rule changes are picked up on-the-fly due to the 
  <property name="ruleReload" value="true"/> on the CBR Action in the jboss-esb.xml
  
  Extra Credit 2: Open jboss-esb.xml and find the GreenService
  replace "<!-- Inject the transformer actions here -->"
  with:
      <action name="transform" class="org.jboss.soa.esb.actions.converters.SmooksTransformer">
         <property name="resource-config" value="/smooks-res.xml" />
      </action>

      <action name="convertPOJO2Message" class="org.jboss.soa.esb.dvdstore.DVDStoreAction"	/>
      <!-- this will send everything to the console -->
      <action name="dump" class="org.jboss.soa.esb.actions.SystemPrintln">
       		<property name="printfull" value="true"/>
      </action>
      
  Then "ant runtest".  The jboss-esb.xml changes will be picked up automatically assuming 
  you are running with "bootstrapper" via "ant run".  If you are running as a .esb archive 
  then you'll need to edit the jboss-esb.xml in the deploy folder of your ESB Server or your
  Application Server.
