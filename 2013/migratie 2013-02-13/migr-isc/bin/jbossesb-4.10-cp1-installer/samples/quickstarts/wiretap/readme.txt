Overview:
=========
  The purpose of the wiretap quickstart sample is demonstrate the wiretap J2EE
  pattern (http://www.enterpriseintegrationpatterns.com/WireTap.html) using a
  static router as implemented in org.jboss.soa.esb.actions.StaticRouter and a
  content based router (CBR) as implemented in
  org.jboss.soa.esb.actions.ContentBasedRouter

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.


To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant receive-destination'.
  3. Open another command terminal window in this folder ("Window3"), type
     'ant receive-wiretap'.
  4. run the test ("Window1"), type 'ant runtest-cbr'.
  5. Switch back to Application Server, "Window2" and "Window3" to see the output from
     the ESB
  6. When finished, interrupt the receivers using Ctrl-C.
  7. Undeploy the esb archive, type 'ant undeploy'.

Things to Consider:
===================
  The quickstart makes use of the queues illustrated (the queues are shown 
  in boxes) in the following diagram. You might review the build.xml file to 
  see how it is setup. More details on the build.xml can be found later in 
  this document. You can modify the build.xmlto change the phrase sent in the
  messages.

     application initiated
              |
              |
              V
   --------------------------     ---------------------------- 
   |qswiretap_wire_GWRequest| --> |qswiretap_wire_out_Request|--> wiretap output
   --------------------------     ---------------------------- 
              |
              |
              V
   ---------------------------
   |qswiretap_regular_Request|
   ---------------------------
              |
              |
              V
     destination output  

Project File Descriptions
=========================
  You can modify the "routeToFollow" element value in SampleOrder.xml to control
  the path that is followed thru the CBR. The valid values are: "regular,",
  "wiretap," and "both."

  * build.xml - The following targets are also supported
    * runtest - send message through the gateway listener in MyJMSListener
      directly to destination queue, bypasses wiretap completely.

    * wiretap - send message through the gateway listener MyJMSWiretapListener
      to wiretap output queue

    * static-router - send message through the static router gateway in
      org.jboss.soa.esb.actions.StaticRouter to both wiretap output queue and
      destination queue

    * runtest-cbr - send message throught the CBR router gateway in
      org.jboss.soa.esb.actions.ContentBasedRouter to both wiretap output queue
      and destination queue
