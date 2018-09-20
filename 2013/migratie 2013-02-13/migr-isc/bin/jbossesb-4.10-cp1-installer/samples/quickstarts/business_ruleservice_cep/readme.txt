Note:
=====
  This example is currently under development and will most likely change in
  the next release of JBoss ESB to more accurately showcase a CEP application.

Overview:
=========
  An example of Complex Event Processing, using the classic "hub and spoke"
  model to maintain a heartbeat status for a list of spokes, and an inventory
  status for a list of parts for each spoke.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  You will use "Window1" for the rest of these instructions.

  2. Type 'ant usage'.  This will tell you the ant targets for this quickstart.

  3. Type 'ant startSession'.  This will start the Stateful Rule Session on the
  server. Server console output will look like this:

  14:13:17,468 INFO  [LogSystemEventListener] Message [KnowledegAgent has
  started listening for ChangeSet notifications]
  14:13:17,468 INFO  [LogSystemEventListener] Message [KnowledgeAgent created,
  with configuration:
  monitorChangeSetEvents=true scanResources=true scanDirectories=true
  newInstance=true]
  14:13:17,469 INFO  [LogAgentEventListener] Agent [business_ruleservice_cep]
  Message [KnowledgeAgent applying ChangeSet]
  14:13:20,332 INFO  [LogAgentEventListener] Agent [business_ruleservice_cep]
  Message [KnowledgeAgent new KnowledgeBase now built and in use]

  Every 30 seconds, the "monitor X rule"s (where "X" is Atlanta, Boulder or
  Chicago), notice that it hasn't received a heartbeat status in a while, so
  server console output will look like this:

  14:13:50,453 WARN  [Hub] heartbeat is RED for Atlanta :(
  14:13:50,458 WARN  [Hub] heartbeat is RED for Chicago :(
  14:13:50,459 WARN  [Hub] heartbeat is RED for Boulder :(

  4. To send a heartbeat for a particular Spoke, type
  	'ant -Dspokes="Atlanta" sendHeartbeat'
	This will produce server console output like:

  14:18:10,301 INFO  [Hub] received Heartbeat for Atlanta
  14:18:10,306 INFO  [Hub] heartbeat is GREEN for Atlanta :)
  14:18:20,458 WARN  [Hub] heartbeat is RED for Chicago :(
  14:18:20,459 WARN  [Hub] heartbeat is RED for Boulder :(
  
	You could also have typed
  	'ant -Dspokes="Atlanta Boulder Chicago" sendHeartbeat'
	To send a heartbeat for all the Spokes.  Eventually, the heartbeat for each
	spoke will go RED again, until you send another heartbeat.

  5. The Hub maintains a pseudo-database for each Spoke's  Parts inventory, in
  addition to the pseudo-database for the Spokes' hearbeat status.  To
  initially pre-populate this part inventory, type 'ant populateInventory'.
  This will produce server console output like this:

  14:22:21,252 INFO  [Hub] will update inventory for Chicago:sprocket
  (oldCount=0, newCount=8)
  14:22:21,253 INFO  [WarehouseChannel] will notify the Denver warehouse of:
  InventoryStatus: id=1, spokeLocation=Chicago, partName=sprocket, adjustment=8
  14:22:21,267 INFO  [Hub] will update inventory for Chicago:widget
  (oldCount=0, newCount=9)
  14:22:21,267 INFO  [WarehouseChannel] will notify the Denver warehouse of:
  InventoryStatus: id=2, spokeLocation=Chicago, partName=widget, adjustment=9
  14:22:21,279 INFO  [Hub] inventory is GREEN for Chicago :)
  ...

  and so on, for each Part for each Spoke.  You can see that non only is the
  inventory status GREEN, but the act of receiving an inventory status change
  also acts as a heartbeat for that particular spoke.  So, you will
  additionally see this server console output:

  14:22:21,279 INFO  [Hub] heartbeat is GREEN for Chicago :)
  ...

  6. To increment a Part's count for a Spoke in the Hub's inventory, type
  'ant -Dparts="Atlanta:widget" incrementPart'
  This will product server console output like:

  14:26:00,560 INFO  [Hub] will update inventory for Atlanta:widget
  (oldCount=6, newCount=7)
  14:26:00,560 INFO  [WarehouseChannel] will notify the Denver warehouse of:
  InventoryStatus: id=1, spokeLocation=Atlanta, partName=widget, adjustment=1
  14:26:00,572 INFO  [Hub] inventory is GREEN for Atlanta :)
  14:26:00,572 INFO  [Hub] heartbeat is GREEN for Atlanta :)
  14:26:00,576 INFO  [STDOUT] DistributionService received Message: 
  14:26:00,576 INFO  [STDOUT] [InventoryStatus: id=1, spokeLocation=Atlanta,
  partName=widget, adjustment=1].

  You could also have passed a list of Spoke locations and Parts to increment:
  'ant -Dparts="Atlanta:widget Boulder:sprocket Chicago:nut Atlanta:bolt" incrementPart'

  You can see that the custom WarehouseChannel is notified via the
  <property name="channels"> section of jboss-esb.xml, as well as the built-in
  ServiceChannel, which uses ServiceInvoker to send a Message to another ESB
  Service.

  7. To decrement a Part's count for a Spoke in the Hub's inventory, type
  'ant -Dparts="Atlanta:widget" decrementPart'
  This will product server console output like:

  14:28:28,432 INFO  [Hub] will update inventory for Atlanta:widget
  (oldCount=7, newCount=6)
  14:28:28,432 INFO  [WarehouseChannel] will notify the Denver warehouse of:
  InventoryStatus: id=1, spokeLocation=Atlanta, partName=widget, adjustment=-1
  14:28:28,438 INFO  [STDOUT] DistributionService received Message: 
  14:28:28,438 INFO  [STDOUT] [InventoryStatus: id=1, spokeLocation=Atlanta,
  partName=widget, adjustment=-1].
  14:28:28,438 INFO  [Hub] inventory is GREEN for Atlanta :)
  14:28:28,438 INFO  [Hub] heartbeat is GREEN for Atlanta :)

  You could also have passed a list of Spoke locations and Parts to increment:
  'ant -Dparts="Boulder:widget Chicago:sprocket Atlanta:nut Boulder:bolt" decrementPart'

  If you keep decrementing a Part for a Spoke until the count in the inventory
  would become less than zero, you are blocked and will see server console
  output like this:

  14:32:01,801 WARN  [Hub] will NOT update inventory for Atlanta:widget
  (oldCount=0, newCount=-1)
  14:32:01,801 INFO  [WarehouseChannel] will notify the Denver warehouse of:
  InventoryStatus: id=1, spokeLocation=Atlanta, partName=widget, adjustment=-1
  14:32:01,807 INFO  [STDOUT] DistributionService received Message: 
  14:32:01,807 INFO  [STDOUT] [InventoryStatus: id=1, spokeLocation=Atlanta,
  partName=widget, adjustment=-1].
  14:32:01,807 WARN  [Hub] inventory is RED for Atlanta :(
  14:32:01,807 INFO  [WarehouseChannel] will notify the Denver warehouse of:
  Event: type=INVENTORY, light=RED, spokeLocation=Atlanta 

  8. Type 'ant stopSession'.  This will stop the Stateful Rule Session on the
  server. By default, no server console output will be visible.  You can change
  your log4j settings to alter this.

Project file descriptions:
==========================
	jboss-esb.xml:
		* This ESB deployment's configuration file, defining all providers and
		Services.  The intersting part for this quickstart is the configuration
		of the BusinessRulesProcessor, where a ruleFireMethod of
		"FIRE_UNTIL_HALT" is defined, as is a ruleEventProcessingType of "STREAM"
		and a ruleClockType of "REALTIME".  You can also uncomment the
		ruleAuditType, ruleAuditFile and ruleAuditInterval properties if so
		desired.  For an explanation of what these properties mean, please
		refer to the JBoss ESB Services Guide and Programmer's Guide.

	hornetq-jms.xml, jbm-queue-service.xml, jbmq-queue-service.xml:
		* JMS configuration files, one for HornetQ, one for JBoss Messaging,
		and one for JBossMQ.

	deployment.xml:
		* Defines the deployment dependencies for this quickstart.

	business_ruleservice_cep.drl:
		* The file which defines the Drools rules, and pointed to by
		business_ruleservice_cep.properties.

	business_ruleservice_cep-unfiltered.properties:
		* Will be filtered by ant, becoming business_ruleservice_cep.properties:
		the ruleAgentProperties property the BusinessRulesProcessor is
		configured to use in jboss-esb.xml.

	src/.../hub/Hub.java:
		* Represents a Hub in the classic "hub and spoke" model.
	src/.../hub/Event.java:
		* Represents a Drools @role(event) object.
		(See: business_ruleservice_cep.drl)
	src/.../hub/WarehouseChannel.java:
		* A simple example of a Drools org.drools.runtime.Channel
		(used to be called "ExitPoint")

	src/.../spoke/Spoke.java:
		* Represents a Spoke in the classic "hub and spoke" model.
	src/.../spoke/Part.java:
		* Represents a generic Part that is maintained at the Spoke.

	src/.../status/Status.java:
		* A generic piece of information that gets sent from a Spoke to the Hub.
	src/.../status/HeartbeatStatus.java:
		* A Status letting the Hub know that a Spoke is okay.
	src/.../status/InventoryStatus.java:
		* A Status letting the Hub know that a Part's count should be
		incremented or decremented in the Hub's inventory psuedo-database.
	src/.../status/StatusSender.java:
		* Sends JMS Messages to the server, containing a Status.
	src/.../status/StatusFilter.java:
		* A transformer of sorts, modifying the message by putting the Status
		payload in a particular location where the BusinessRulesProcessor
		expects it to be.

	src/.../test/Test.java:
		* Client interface to exercise the quickstart.
		(See: build.xml targets and structure description below)

	log4j.xml:
	Needed to configure log4J used by the quickstart.

	build.xml:
	Targets and structure description:
		* usage:				echoes quickstart target usage
		* startSession:			starts the stateful rule session
		* sendHeartbeat:		sends a heartbeat to a spoke or list of spokes
		* populateInventory:	randomly populates the inventory
		* incrementPart:		increments a part or list of parts, per spoke
		* decrementPart:		decrements a part or list of parts, per spoke
		* stopSession:			stops the stateful rule session

