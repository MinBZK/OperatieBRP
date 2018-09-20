Overview:
=========
  The purpose of the ejbprocesser quickstart is to demonstrate the EJBProcessor action.
  The quickstart will use the EJBProcessor to call two methods on a Stateless Session Bean(SLSB).
  The first call will invoke a method that takes arguments and the second call will 
  invoke a method that returns a value.

  Note, that this quickstart requires an EJB container/deployer to run.
  
Running this quickstart:
========================
  Please refer to 'ant help-quickstarts' for prerequisites about the quickstarts
  and a more detailed descripton of the different ways to run the quickstarts.

To Run:
===========================
  1. Type 'ant deploy'.
  2. Type 'ant runtest' -- invoke service by sending a JMS Message to gateway
  3. Type 'ant sendesb' -- invoke service directly 
  4. Type 'ant undeploy'

What to look for in this quickstart
===================================
	ejb:
	This directory contains the Statless Session Bean used in this example.

	Expected output:
	10:54:13,111 INFO  [STDOUT] SimpleSLSB printMessage : Hello EJBProcessor Service
	10:54:13,113 INFO  [STDOUT] Value from getMessage: 
	10:54:13,113 INFO  [STDOUT] [Some text from SimpleSLSB].

