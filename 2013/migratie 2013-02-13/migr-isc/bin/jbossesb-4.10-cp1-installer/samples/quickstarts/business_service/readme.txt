Overview:
=========
  This example demonstrates the how to access an EJB3 Stateless Session Bean 
  from within an ESB Action.  ESB Actions are primarily custom mediation
  components which allow you to introduce different specialized transformations, 
  routing behaviors, orchestration, etc. The EJB3 is a simple "Hello World" 
  annotated POJO.

Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed description of the different ways to run the quickstarts.

  ********************************************************************************
  * IMPORTANT NOTE!: this quickstart requires an ESB embedded in JBoss AS 4.2.x. *
  * Installation instructions can be found in the install/readme.txt.            *
  ********************************************************************************

To Run:
===========================
  1. In a command terminal window in this folder ("Window1"), type 'ant deploy'.
  2. Open another command terminal window in this folder ("Window2"), type
     'ant runtest'.
  3. Switch back to Application Server console to see the output from the ESB
  4. In this folder ("Window1"), type 'ant undeploy'.
